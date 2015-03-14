(progn
    (sly-slides
     '(file "~/github/conf-talks/2015-rubyfuza/sly/title_slide.jpg")
     '(file "/home/arne/ticketsolve/repo/app/assets/images/ticketsolve_bird_logo.png")
     '(file "~/github/conf-talks/2015-rubyfuza/sly/once_upon_a_time.jpg")
     '(file "~/github/conf-talks/2015-rubyfuza/sly/api_like_the_web.jpg")
     '(file "~/github/conf-talks/2015-rubyfuza/sly/yaks.png")
     '(file "~/github/conf-talks/2015-rubyfuza/sly/cart_mapper.rb"
            scale 4)
     '(file "~/github/conf-talks/2015-rubyfuza/sly/wexford-show.png")
     '(file "~/github/conf-talks/2015-rubyfuza/sly/shows.json"
            scale 4)
     '(file "~/github/conf-talks/2015-rubyfuza/sly/pager.png")
     '(file "~/github/conf-talks/2015-rubyfuza/sly/shows2.json"
            scale 7)
     '(file "~/github/conf-talks/2015-rubyfuza/sly/calendar.png")
     '(file "~/github/conf-talks/2015-rubyfuza/sly/shows3.json"
            scale 7)
     '(file "~/github/conf-talks/2015-rubyfuza/sly/book_tickets.png")
     '(file "~/github/conf-talks/2015-rubyfuza/sly/event.json"
            scale 5)
     '(file "~/github/conf-talks/2015-rubyfuza/sly/event2.json"
            scale 5)
     '(file "~/github/conf-talks/2015-rubyfuza/sly/cart.png")
     '(file "~/github/conf-talks/2015-rubyfuza/sly/change_quantity.js"
            scale 5)
     '(file "~/github/conf-talks/2015-rubyfuza/sly/formats.md"
            scale 5)
     '(file "~/github/conf-talks/2015-rubyfuza/sly/html0.png")
     '(file "~/github/conf-talks/2015-rubyfuza/sly/html.png")
     '(file "~/github/conf-talks/2015-rubyfuza/sly/fin.jpg"))

    (run-at-time 17 17 'sly-forward))

(cancel-timer (car timer-list))
