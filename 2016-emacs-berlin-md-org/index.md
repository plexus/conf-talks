---

## Emacs Berlin

Welcome!

Pizzas by Lambda Island, drinks by Contentful! <3

## Wifi

Contentful Guest

password: casacontenta

(network maintenance at 19:00)

----
{:#cover}

## Treating Markdown As org-mode

A presentation by [Arne Brasseur](https://devblog.arnebrasseur.net)

for [Emacs Berlin, September 2015](http://emacs-berlin.org/).

28 September 2016

----

## What already works

Cycling through headings

- `Tab` Cycle current header
- `S-Tab` Cycle all headers

----

## What already works

Promoto/demote/move entire subtrees

- `M-S-<left>` Promote
- `M-S-<right>` Demote
- `M-S-<up>` Move up
- `M-S-<down>` Move down

----

## What already works

Follow links:

- `C-c C-o` Open link at point

----

## Creating links

Bindings are different

- org-mode `C-c C-l`
- markdown-mode `C-c C-a l`

Behaviour is different

----

## Creating links

``` clojure
(defun plexus/md-insert-link ()
  (interactive)
  (let ((link (read-string "Link: "))
        (desc (read-string "Description: ")))
    (when (string= "" desc)
      (setq desc
          (buffer-substring-no-properties (mark) (point)))
      (delete-region (mark) (point)))
    (insert (concat "[" desc "](" link ")"))))

(define-key
  markdown-mode-map
  (kbd "C-c C-l")
  'plexus/md-insert-link)
```

----

## Code blocks

Inserting in org-mode: `< s TAB`

Inserting in gfm-mode: \`\`\`

Editing in org-mode: `C-c C-'`

Editing in markdown-mode: does not exist

----

## Code blocks

Insert like in org-mode, with Yasnippet

`M-x yas-new-snippet`


```
# -*- mode: snippet -*-
# name: source-block
# key: <s
# --
\`\`\` $1
$2
\`\`\`
```

---

## Code blocks

``` clojure
(define-key markdown-mode-map (kbd "C-c '")
            'plexus/edit-md-source-block)

(define-key plexus/restore-mode-map (kbd "C-c '")
            'plexus/restore-md-source-block)

(defvar plexus/restore-mode-map (make-sparse-keymap)
  "Keymap for plexus/restore-mode.")

(define-minor-mode plexus/restore-mode
  "Temporary minor mode to return to the markdown file"
  nil
  :lighter " ♻"
  plexus/restore-mode-map)
```

---

## Code blocks


``` clojure
(defun plexus/edit-md-source-block ()
  (interactive)
  (let ((buffer nil))
    (save-excursion
      (re-search-backward "\n```\[a-z- \]+\n")
      (re-search-forward "\n``` *")
      (let ((lang (thing-at-point 'symbol))
            (md-buffer (current-buffer)))
        (forward-line)
        (let ((start (point)))
          (re-search-forward "\n```")
          (let* (...)
            (setq buffer ...))
    (switch-to-buffer buffer)
    (plexus/restore-mode 1)))
```

---

## Code blocks

``` clojure
(let* ((end (- (point) 4))
       (source (buffer-substring-no-properties start end)))
  (setq buffer (get-buffer-create (concat "*" lang "*")))
  (set-buffer buffer)
  (erase-buffer)
  (insert source)
  (setq restore-start start)
  (setq restore-end end)
  (setq restore-buffer md-buffer)
  (make-local-variable 'restore-start)
  (make-local-variable 'restore-end)
  (make-local-variable 'restore-buffer)
  (funcall (intern (concat (downcase lang) "-mode"))))
```

---

## Code blocks

``` clojure
(defun plexus/restore-md-source-block ()
  (interactive)
  (let ((contents (buffer-string))
        (edit-buffer (current-buffer)))
    (save-excursion
     (set-buffer restore-buffer)
     (delete-region restore-start restore-end)
     (goto-char restore-start)
     (insert contents))
    (switch-to-buffer restore-buffer)
    (kill-buffer edit-buffer)))
```
