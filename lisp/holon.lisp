(defpackage :holon
  (:use :cl)
  (:export :holon :inferior-holons))

(in-package :holon)

(defclass holon ()
  ((name :initarg :name)
   (inferior-holons :initform '()
		    :initarg :inferior-holons
		    :accessor inferior-holons)
   (superior-holons :initform '() :initarg :superior-holons)
   (state :initform 'intact)))

(defun cascade (holon fn)
  (funcall fn holon)
  (loop for holon in (slot-value holon 'inferior-holons)
	do (cascade holon fn)))

(defun shake (holon)
  (cascade holon #'renew))

(defgeneric renew (holon))
(defgeneric disolve (holon))

(defmethod renew ((holon holon))
  (format t "An abstract holon is like a mathematical point.~%Therefore, there is nothing to it, so it can't be renewed"))

(defmethod disolve ((holon holon))
  (dolist (inferior-holon (slot-value holon 'inferior-holons))
    (disolve inferior-holon))
  (setf (slot-value holon 'state) 'disolved))


