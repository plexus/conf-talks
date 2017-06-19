;; inferior lisp
(setq inferior-lisp-program "lumo -d")

(define-key clojure-mode-map (kbd "C-x C-e") 'lisp-eval-last-sexp)
(define-key clojure-mode-map (kbd "C-M-x")   'lisp-eval-defun)
(define-key clojure-mode-map (kbd "C-x C-r") 'lisp-eval-region)


;; inf-clojure
(setq inf-clojure-program "planck -d")
(add-hook 'clojure-mode-hook 'inf-clojure-minor-mode)
