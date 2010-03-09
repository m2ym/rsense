;;; rsense.el --- RSense frontend for Emacs

;; Copyright (C) 2010  Tomohiro Matsuyama

;; Author: Tomohiro Matsuyama <m2ym.pub@gmail.com>
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

(defun rsense-choose-dir (&rest dirs)
  (loop for dir in dirs
        if (file-directory-p dir)
        return (expand-file-name dir)))

(defcustom rsense-home (if (eq system-type 'windows-nt)
                           "C:\\rsense"
                         (rsense-choose-dir "~/opt/rsense"
                                            "~/src/rsense"
                                            "/opt/rsense"))
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

(defcustom rsense-debug t
  "Non-nil means RSense runs on debug mode.")

(defcustom rsense-temp-file nil
  "Temporary file for containing uncomplete buffer.")

(defcustom rsense-rurema-home (if (eq system-type 'windows-nt)
                                  "C:\\rurema"
                                (rsense-choose-dir "~/opt/rurema"
                                                   "~/src/rurema"
                                                   "/opt/rurema"))
  "Home directory of Ruby Reference Manual Project.")

(defcustom rsense-rurema-refe "refe-1_8_7"
  "Program name of ReFe.")

(defsubst rsense-interpreter ()
  (if (boundp 'ruby-program)
      ruby-program
    "ruby"))

(defsubst rsense-program ()
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

(defun rsense-command (&rest command)
  (car-safe
   (read-from-string
    (with-output-to-string
      (with-current-buffer standard-output
        (rsense-command-1 command nil))))))

(defun rsense-command-no-output (&rest command)
  (rsense-command-1 command t))

(defun rsense-buffer-command (buffer offset command &optional remove-until prefix)
  (unless rsense-temp-file
    (setq rsense-temp-file (make-temp-file "")))
  (with-temp-buffer
    (insert (with-current-buffer buffer (buffer-string)))
    (if remove-until
        (delete-region offset remove-until))
    (when prefix
      (goto-char offset)
      (insert prefix)
      (incf offset (length prefix)))
    (write-region (point-min) (point-max) rsense-temp-file nil 0)
    (rsense-command command
                    (format "--file=%s" rsense-temp-file)
                    (format "--encoding=UTF-8")
                    (format "--location=%s" (1- offset)))))

(defun rsense-code-completion (&optional buffer offset remove-until prefix)
  (rsense-buffer-command (or buffer (current-buffer))
                         (or offset (point))
                         "code-completion"
                         remove-until prefix))

(defun rsense-type-inference (&optional buffer offset)
  (rsense-buffer-command (or buffer (current-buffer))
                         (or offset (point))
                         "type-inference"))

(defun rsense-lookup-document (pattern)
  (shell-command-to-string (format "%s/%s '%s'" rsense-rurema-home rsense-rurema-refe pattern)))

(defun rsense-version ()
  (interactive)
  (message "%s" (rsense-command "version")))

(defun rsense-version ()
  (interactive)
  (message "%s" (rsense-command "version")))

(defun rsense-clear ()
  (interactive)
  (rsense-command-no-output "clear"))

(defun rsense-type-help ()
  (interactive)
  (let ((result (assoc-default 'type (rsense-type-inference (current-buffer) (point)))))
    (popup-tip (if result
                   (mapconcat 'identity result " | ")
                 "No type information")
               :margin t)))

(defun ac-rsense-documentation (item)
  (ignore-errors
    (rsense-lookup-document (cadr item))))

(defun ac-rsense-candidates (&optional prefix)
  (mapcar (lambda (entry)
            (cons (car entry) entry))
          (assoc-default 'completion
                         (rsense-code-completion (current-buffer)
                                                 ac-point
                                                 (point)
                                                 prefix))))

(ac-define-source rsense
  '((candidates . ac-rsense-candidates)
    (prefix . "\\(?:\\.\\|::\\)\\(.*\\)")
    (document . ac-rsense-documentation)
    (cache)))

(provide 'rsense)
;;; rsense.el ends here
