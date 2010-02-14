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

(defcustom rsense-home (expand-file-name "~/src/rsense")
  "Home directory of RSense.")

(defcustom rsense-temp-file "/tmp/rsense-buf"
  "Temporary file for containing uncomplete buffer.")

(defcustom rsense-debug t
  "Non-nil means RSense runs on debug mode.")

(defcustom rsense-log-file "/tmp/rsense.log"
  "RSense log file.")

(defcustom rsense-rurema-home (expand-file-name "~/src/rurema")
  "Home directory of Ruby Reference Manual Project.")

(defcustom rsense-rurema-refe "refe-1_8_7"
  "Program name of ReFe.")

(defun rsense-command (&rest command)
  (setenv "RSENSE_LOG" rsense-log-file)
  (setenv "RSENSE_DEBUG" (if rsense-debug "true"))
  (car-safe
   (read-from-string
    (shell-command-to-string (format "%s/bin/rsense %s --format=emacs"
                                     rsense-home
                                     (mapconcat 'identity command " ")
                                     rsense-log-file)))))

(defun rsense-buffer-command (buffer offset command &optional remove-until prefix)
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

(defun rsense-code-completion (buffer offset &optional remove-until prefix)
  (rsense-buffer-command buffer offset "code-completion" remove-until prefix))

(defun rsense-type-inference (buffer offset)
  (rsense-buffer-command buffer offset "type-inference"))

(defun rsense-type-help ()
  (interactive)
  (let ((result (assoc-default 'type (rsense-type-inference (current-buffer) (point)))))
    (popup-tip (if result
                   (mapconcat 'identity result " | ")
                 "No type information")
               :margin t)))

(defun rsense-lookup-document (pattern)
  (shell-command-to-string (format "%s/%s '%s'" rsense-rurema-home rsense-rurema-refe pattern)))

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

(defvar ac-source-rsense
  '((candidates . ac-rsense-candidates)
    (prefix . "\\(?:\\.\\|::\\)\\(.*\\)")
    (document . ac-rsense-documentation)
    (cache)))

(defun rsense-complete ()
  (interactive)
  (auto-complete '(((candidates . (ac-rsense-candidates "self"))
                    (prefix . "\\(\\sw\\|\\s_\\)*")
                    (document . ac-rsense-documentation)
                    (cache)))))

(provide 'rsense)
;;; rsense.el ends here
