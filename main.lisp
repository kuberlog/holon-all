(ql:quickload :inferior-shell)
(ql:quickload :cl-store)
(ql:quickload :parachute)
(ql:quickload :postmodern)

(defpackage :todo
  (:use :cl :parachute :postmodern)
  (:export :select :complete :deselect :add-todo))

(in-package :todo)

(defun run-all-package-tests ()
  (test 'select-group-test)
  (test 'add-todo-test)
  (test 'find-todo-test)
  (test 'test-select))

(defun init ()
  (load "todo/globals.lisp")
  (load "todo/todo.lisp")
  (load "todo/todo-list.lisp")
  (load "todo/printing.lisp")
  (load "todo/api.lisp")
  (load "todo/storage.lisp")
  (load "todo/hooks.lisp"))

(init)

;; A way to integrate todo with the rest of alexandria is to provide a macro with-todos that dynamically lets *todo-list* to the todo 
