;;; rsense.el --- RSense client for Emacs

;; Copyright (C) 2010  Tomohiro Matsuyama

;; Author: Tomohiro Matsuyama <tomo@cx4a.org>
;; Keywords: convenience

;; This program is free software; you can redistribute it and/or modify
;; it under the terms of the GNU General Public License as published by
;; the Free Software Foundation, either version 3 of the License, or
;; (at your option) any later version.

;; This program is distributed in the hope that it will be useful,
;; but WITHOUT ANY WARRANTY; without even the implied warranty of
;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
;; GNU General Public License for more details.

;; You should have received a copy of the GNU General Public License
;; along with this program.  If not, see <http://www.gnu.org/licenses/>.

;;; Commentary:

;; 

;;; Code:

(defcustom rsense-home nil
  "Home directory of RSense.")

(defcustom rsense-socket nil
  "Specify socket. File name means UNIX domain socket. <host>:<port> means TCP socket.
Nil means proper socket will be selected.")

(defcustom rsense-classpath nil
  "Classpath giving to RSense backend.")

(defcustom rsense-pid-file nil
  "Pid file path giving to RSense backend.")

(defcustom rsense-log-file nil
  "RSense log file.")

(defcustom rsense-debug nil
  "Non-nil means RSense runs on debug mode.")

(defcustom rsense-temp-file nil
  "Temporary file for containing uncomplete buffer.")

(defcustom rsense-rurema-home nil
  "Home directory of Ruby Reference Manual Project.")

(defcustom rsense-rurema-refe "refe-1_8_7"
  "Program name of ReFe.")

(defun rsense-interpreter ()
  (if (boundp 'ruby-program)
      ruby-program
    "ruby"))

(defun rsense-program ()
  (concat rsense-home "/bin/rsense"))

(defun rsense-args (&rest args)
  (delq nil
        (append (list (concat "--home=" rsense-home)
                      (if rsense-socket
                          (concat "--socket=" rsense-socket))
                      (if rsense-classpath
                          (concat "--classpath=" rsense-classpath))
                      (if rsense-pid-file
                          (concat "--pid-file=" rsense-pid-file))
                      (if rsense-log-file
                          (concat "--log=" rsense-log-file))
                      (if rsense-debug
                          "--debug")
                      "--")
                args)))

(defun rsense-command-1 (command no-output)
  (apply 'call-process
         (rsense-interpreter)
         nil (not no-output) nil
         (cons (rsense-program)
               (apply 'rsense-args
                      (append command '("--format=emacs"))))))

(defun rsense-command-to-string (&rest command)
  (with-output-to-string
      (with-current-buffer standard-output
        (rsense-command-1 command nil))))

(defun rsense-command-no-output (&rest command)
  (rsense-command-1 command t))

(defun rsense-command (&rest command)
  (let ((output (apply 'rsense-command-to-string command)))
    (car-safe (read-from-string output))))

(defun* rsense-buffer-command (buffer
                               command
                               &key
                               remove-until
                               offset
                               line)
  (unless rsense-temp-file
    (setq rsense-temp-file (make-temp-file temporary-file-directory)))
  (with-temp-buffer
    (insert (with-current-buffer buffer (buffer-string)))
    (if remove-until
        (delete-region offset remove-until))
    (write-region (point-min) (point-max) rsense-temp-file nil 0)
    (rsense-command command
                    (format "--file=%s" rsense-temp-file)
                    (format "--encoding=UTF-8")
                    (cond
                     (offset
                      (format "--location=%s" (1- offset)))
                     (line
                      (format "--line=%s" line)))
                    (format "--detect-project=%s" (buffer-file-name buffer)))))

(defun rsense-code-completion (&optional buffer offset remove-until)
  (rsense-buffer-command (or buffer (current-buffer))
                         "code-completion"
                         :offset (or offset (point))
                         :remove-until remove-until))

(defun rsense-type-inference (&optional buffer offset)
  (rsense-buffer-command (or buffer (current-buffer))
                         "type-inference"
                         :offset (or offset (point))))

(defun rsense-find-definition (&optional buffer offset)
  (rsense-buffer-command (or buffer (current-buffer))
                         "find-definition"
                         :offset (or offset (point))))

(defun rsense-where (&optional buffer offset)
  (rsense-buffer-command (or buffer (current-buffer))
                         "where"
                         :line (line-number-at-pos offset)))

(defun rsense-lookup-document (pattern)
  (when (file-directory-p rsense-rurema-home)
    (shell-command-to-string (format "%s/%s '%s'" rsense-rurema-home rsense-rurema-refe pattern))))

(defun rsense-complete ()
  (interactive)
  (if (save-excursion (re-search-backward "\\(?:\\.\\|::\\)\\(.*\\)\\=" (line-beginning-position) t))
      (let* ((offset (match-beginning 1))
             (point (match-end 0))
             (prefix (match-string 1))
             (list (all-completions prefix
                                    (assoc-default 'completion
                                                   (rsense-code-completion (current-buffer)
                                                                           offset
                                                                           point))))
             (common (try-completion prefix list))
             (buffer "*Completions*"))
        (when (and (stringp common)
                   (not (equal prefix common)))
          (delete-region offset point)
          (insert common)
          (setq prefix common))
        (cond
         ((null list)
          (message "No completions"))
         ((eq (length list) 1)
          (let ((window (get-buffer-window buffer)))
            (if window
                (with-selected-window window
                  (or (window-dedicated-p window)
                      (bury-buffer))))))
         (t
          (with-output-to-temp-buffer buffer
            (display-completion-list list prefix))
          (display-buffer buffer))))))

(defun rsense-type-help ()
  (interactive)
  (let* ((result (assoc-default 'type (rsense-type-inference (current-buffer) (point))))
         (msg (if result
                  (mapconcat 'identity result " | ")
                "No type information")))
    (if (featurep 'popup)
        (popup-tip msg :margin t)
      (message "Type: %s" msg))))

(defun rsense-jump-to-definition ()
  (interactive)
  (let ((locations (assoc-default 'location (rsense-find-definition (current-buffer) (point)))))
    ;; Unmap for tempfile
    (setq locations (mapcar (lambda (loc)
                              (cons (if (equal (car loc) rsense-temp-file) (buffer-file-name) (car loc))
                                    (cdr loc)))
                            locations))
    
    (let (loc)
      (if (and (> (length locations) 1)
               (featurep 'popup))
          (setq loc (popup-menu* (mapcar (lambda (loc)
                                           (popup-make-item (format "%s:%s" (car loc) (cdr loc))
                                                            :value loc))
                                         locations)))
        ;; TODO selection interface
        (setq loc (car locations)))
      (if (null loc)
          (funcall (if (featurep 'popup) 'popup-tip 'message)
                   "No definition found"
                   :margin t)
        (find-file (car loc))
        (goto-line (cdr loc))))))

(defun rsense-where-is ()
  (interactive)
  (let ((name (or (assoc-default 'name (rsense-where (current-buffer) (point)))
                  "Unknown")))
    (if (featurep 'popup)
        (popup-tip name :margin t)
      (message "Name: %s" name))))

(defun rsense-open-project (dir)
  (interactive "DDirectory: ")
  (rsense-command-no-output "open-project" (expand-file-name dir)))

(defun rsense-close-project (project)
  (interactive (list (completing-read "Project: "
                                      (rsense-command "list-project"))))
  (rsense-command-no-output "close-project" project))

(defun rsense-clear ()
  (interactive)
  (rsense-command-no-output "clear"))

(defun rsense-exit ()
  (interactive)
  (rsense-command-no-output "exit"))

(defun rsense-version ()
  (interactive)
  (message "%s" (rsense-command "version")))

(defun rsense-service-start ()
  "Start Windows service."
  (interactive)
  (message "%s" (rsense-command-to-string "service" "start")))

(defun rsense-service-stop ()
  "Stop Windows service."
  (interactive)
  (message "%s" (rsense-command-to-string "service" "stop")))

(defun rsense-service-status ()
  "Show Windows service status."
  (interactive)
  (message "%s" (rsense-command-to-string "service" "status")))

(defun ac-rsense-documentation (item)
  (ignore-errors
    (rsense-lookup-document (cadr item))))

(defun ac-rsense-candidates ()
  (mapcar (lambda (entry)
            (let ((name (nth 0 entry))
                  (qname (nth 1 entry))
                  (base (nth 2 entry))
                  (kind (nth 3 entry)))
              (propertize name
                          'value entry
                          'symbol (assoc-default kind '(("CLASS" . "C")
                                                        ("MODULE" . "M")
                                                        ("CONSTANT" . "c")
                                                        ("METHOD" . "m")))
                          'summary base)))
          (assoc-default 'completion
                         (rsense-code-completion (current-buffer)
                                                 ac-point
                                                 (point)))))

(defvar ac-source-rsense
  '((candidates . ac-rsense-candidates)
    (prefix . "\\(?:\\.\\|::\\)\\(.*\\)")
    (requires . 0)
    (document . ac-rsense-documentation)
    (cache)))
(defvaralias 'ac-source-rsense-method 'ac-source-rsense)

;; deprecated
(defvar ac-source-rsense-constant
  '((candidates . nil)))

(defun ac-complete-rsense ()
  (interactive)
  (auto-complete '(ac-source-rsense)))

(provide 'rsense)
;;; rsense.el ends here
