/*  jQuery Nice Select - v1.0
    https://github.com/hernansartorio/jquery-nice-select
    Made by Hernán Sartorio  */
!function (e) { e.fn.niceSelect = function (t) { function s(t) { t.after(e("<div></div>").addClass("nice-select").addClass(t.attr("class") || "").addClass(t.attr("disabled") ? "disabled" : "").attr("tabindex", t.attr("disabled") ? null : "0").html('<span class="current"></span><ul class="list"></ul>')); var s = t.next(), n = t.find("option"), i = t.find("option:selected"); s.find(".current").html(i.data("display") || i.text()), n.each(function (t) { var n = e(this), i = n.data("display"); s.find("ul").append(e("<li></li>").attr("data-value", n.val()).attr("data-display", i || null).addClass("option" + (n.is(":selected") ? " selected" : "") + (n.is(":disabled") ? " disabled" : "")).html(n.text())) }) } if ("string" == typeof t) return "update" == t ? this.each(function () { var t = e(this), n = e(this).next(".nice-select"), i = n.hasClass("open"); n.length && (n.remove(), s(t), i && t.next().trigger("click")) }) : "destroy" == t ? (this.each(function () { var t = e(this), s = e(this).next(".nice-select"); s.length && (s.remove(), t.css("display", "")) }), 0 == e(".nice-select").length && e(document).off(".nice_select")) : console.log('Method "' + t + '" does not exist.'), this; this.hide(), this.each(function () { var t = e(this); t.next().hasClass("nice-select") || s(t) }), e(document).off(".nice_select"), e(document).on("click.nice_select", ".nice-select", function (t) { var s = e(this); e(".nice-select").not(s).removeClass("open"), s.toggleClass("open"), s.hasClass("open") ? (s.find(".option"), s.find(".focus").removeClass("focus"), s.find(".selected").addClass("focus")) : s.focus() }), e(document).on("click.nice_select", function (t) { 0 === e(t.target).closest(".nice-select").length && e(".nice-select").removeClass("open").find(".option") }), e(document).on("click.nice_select", ".nice-select .option:not(.disabled)", function (t) { var s = e(this), n = s.closest(".nice-select"); n.find(".selected").removeClass("selected"), s.addClass("selected"); var i = s.data("display") || s.text(); n.find(".current").text(i), n.prev("select").val(s.data("value")).trigger("change") }), e(document).on("keydown.nice_select", ".nice-select", function (t) { var s = e(this), n = e(s.find(".focus") || s.find(".list .option.selected")); if (32 == t.keyCode || 13 == t.keyCode) return s.hasClass("open") ? n.trigger("click") : s.trigger("click"), !1; if (40 == t.keyCode) { if (s.hasClass("open")) { var i = n.nextAll(".option:not(.disabled)").first(); i.length > 0 && (s.find(".focus").removeClass("focus"), i.addClass("focus")) } else s.trigger("click"); return !1 } if (38 == t.keyCode) { if (s.hasClass("open")) { var l = n.prevAll(".option:not(.disabled)").first(); l.length > 0 && (s.find(".focus").removeClass("focus"), l.addClass("focus")) } else s.trigger("click"); return !1 } if (27 == t.keyCode) s.hasClass("open") && s.trigger("click"); else if (9 == t.keyCode && s.hasClass("open")) return !1 }); var n = document.createElement("a").style; return n.cssText = "pointer-events:auto", "auto" !== n.pointerEvents && e("html").addClass("no-csspointerevents"), this } }(jQuery);
$(document).ready(function () {
  /********* On scroll heder Sticky *********/
  $(window).scroll(function () {
    var scroll = $(window).scrollTop();
    if (scroll >= 50) {
      $("header").addClass("head-sticky");
    } else {
      $("header").removeClass("head-sticky");
    }
  });
  /********* Wrapper top space ********/
  var header_hright = $('header').outerHeight();
  $('header').next('.wrapper').css('margin-top', header_hright + 'px');
  /********* Announcebar hide ********/
  $('#announceclose').click(function () {
    $('.announcebar').slideUp();
  });
  /********* Mobile Menu ********/
  $("#menu").on("click", function () {
    $(".mobile-menu-wrapper, body").toggleClass("active-menu");
  });
  $(".menu-close-icon svg").on("click", function () {
    $(".mobile-menu-wrapper, body").toggleClass("active-menu");
  });
  /********* Cart Popup ********/
  $('.cart-header').on('click', function (e) {
    e.preventDefault();
    setTimeout(function () {
      $('body').addClass('no-scroll cartOpen');
      $('.overlay').addClass('cart-overlay');
    }, 50);
  });
  $('body').on('click', '.overlay.cart-overlay, .closecart', function (e) {
    e.preventDefault();
    $('.overlay').removeClass('cart-overlay');
    $('body').removeClass('no-scroll cartOpen');
  });
  /********* Mobile Filter Popup ********/
  $('.filter-title').on('click', function (e) {
    e.preventDefault();
    setTimeout(function () {
      $('body').addClass('no-scroll filter-open');
      $('.overlay').addClass('active');
    }, 50);
  });
  $('body').on('click', '.overlay.active, .close-filter', function (e) {
    e.preventDefault();
    $('.overlay').removeClass('active');
    $('body').removeClass('no-scroll filter-open');
  });
  /*********  Header Search Popup  ********/
  $(".search-header a").click(function () {
    $(".search-popup").toggleClass("active");
    $("body").toggleClass("no-scroll");
  });
  $(".close-search").click(function () {
    $(".search-popup").removeClass("active");
    $("body").removeClass("no-scroll");
  });
  /******* Cookie Js *******/
  $('.cookie-close').click(function () {
    $('.cookie').slideUp();
  });
  /******* Subscribe popup Js *******/
  $('.close-sub-btn').click(function () {
    $('.subscribe-popup').slideUp();
    $(".subscribe-overlay").removeClass("open");
  });
  /********* qty spinner ********/
  var quantity = 0;
  $('.quantity-increment').click(function () {
    ;
    var t = $(this).siblings('.quantity');
    var quantity = parseInt($(t).val());
    $(t).val(quantity + 1);
  });
  $('.quantity-decrement').click(function () {
    var t = $(this).siblings('.quantity');
    var quantity = parseInt($(t).val());
    if (quantity > 1) {
      $(t).val(quantity - 1);
    }
  });
  /******  Nice Select  ******/
  $('select').niceSelect();
  /*********  Multi-level accordion nav  ********/
  $('.acnav-label').click(function () {
    var label = $(this);
    var parent = label.parent('.has-children');
    var list = label.siblings('.acnav-list');
    if (parent.hasClass('is-open')) {
      list.slideUp('fast');
      parent.removeClass('is-open');
    }
    else {
      list.slideDown('fast');
      parent.addClass('is-open');
    }
  });  
  // card slider 
  $('.card-slider-main').slick({
    dots: false,
    infinite: true,
    speed: 800,
    slidesToShow: 4,
    slidesToScroll: 1,
    swipeToSlide: true,
    prevArrow: '<button class="slide-arrow slick-prev"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M22.7071 12.7071C22.8946 12.5196 23 12.2652 23 12C23 11.7348 22.8946 11.4804 22.7071 11.2929L11.7071 0.292894C11.3166 -0.0976306 10.6834 -0.0976306 10.2929 0.292894C9.90237 0.683418 9.90237 1.31658 10.2929 1.70711L20.5858 12L10.2929 22.2929C9.90237 22.6834 9.90237 23.3166 10.2929 23.7071C10.6834 24.0976 11.3166 24.0976 11.7071 23.7071L22.7071 12.7071ZM13.7071 12.7071C13.8946 12.5196 14 12.2652 14 12C14 11.7348 13.8946 11.4804 13.7071 11.2929L2.70711 0.292894C2.31658 -0.0976302 1.68342 -0.0976302 1.29289 0.292894C0.902369 0.683419 0.902369 1.31658 1.29289 1.70711L11.5858 12L1.29289 22.2929C0.90237 22.6834 0.90237 23.3166 1.29289 23.7071C1.68342 24.0976 2.31658 24.0976 2.70711 23.7071L13.7071 12.7071Z" fill="#fff"/></svg></button>',
    nextArrow: '<button class="slide-arrow slick-next"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M22.7071 12.7071C22.8946 12.5196 23 12.2652 23 12C23 11.7348 22.8946 11.4804 22.7071 11.2929L11.7071 0.292894C11.3166 -0.0976306 10.6834 -0.0976306 10.2929 0.292894C9.90237 0.683418 9.90237 1.31658 10.2929 1.70711L20.5858 12L10.2929 22.2929C9.90237 22.6834 9.90237 23.3166 10.2929 23.7071C10.6834 24.0976 11.3166 24.0976 11.7071 23.7071L22.7071 12.7071ZM13.7071 12.7071C13.8946 12.5196 14 12.2652 14 12C14 11.7348 13.8946 11.4804 13.7071 11.2929L2.70711 0.292894C2.31658 -0.0976302 1.68342 -0.0976302 1.29289 0.292894C0.902369 0.683419 0.902369 1.31658 1.29289 1.70711L11.5858 12L1.29289 22.2929C0.90237 22.6834 0.90237 23.3166 1.29289 23.7071C1.68342 24.0976 2.31658 24.0976 2.70711 23.7071L13.7071 12.7071Z" fill="#fff"/></svg></button>',
    responsive: [
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: 3,
        }
      },
      {
        breakpoint: 767,
        settings: {
          slidesToShow: 2,
        }
      },
      {
        breakpoint: 480,
        settings: {
          slidesToShow: 1,
        }
      }
    ]
  });
  //   pro ring slider 
  $('.pro-ring-main').slick({
    dots: false,
    infinite: true,
    speed: 800,
    slidesToShow: 2,
    slidesToScroll: 1,
    swipeToSlide: true,
    prevArrow: '<button class="slide-arrow slick-prev"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M22.7071 12.7071C22.8946 12.5196 23 12.2652 23 12C23 11.7348 22.8946 11.4804 22.7071 11.2929L11.7071 0.292894C11.3166 -0.0976306 10.6834 -0.0976306 10.2929 0.292894C9.90237 0.683418 9.90237 1.31658 10.2929 1.70711L20.5858 12L10.2929 22.2929C9.90237 22.6834 9.90237 23.3166 10.2929 23.7071C10.6834 24.0976 11.3166 24.0976 11.7071 23.7071L22.7071 12.7071ZM13.7071 12.7071C13.8946 12.5196 14 12.2652 14 12C14 11.7348 13.8946 11.4804 13.7071 11.2929L2.70711 0.292894C2.31658 -0.0976302 1.68342 -0.0976302 1.29289 0.292894C0.902369 0.683419 0.902369 1.31658 1.29289 1.70711L11.5858 12L1.29289 22.2929C0.90237 22.6834 0.90237 23.3166 1.29289 23.7071C1.68342 24.0976 2.31658 24.0976 2.70711 23.7071L13.7071 12.7071Z" fill="#fff"/></svg></button>',
    nextArrow: '<button class="slide-arrow slick-next"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M22.7071 12.7071C22.8946 12.5196 23 12.2652 23 12C23 11.7348 22.8946 11.4804 22.7071 11.2929L11.7071 0.292894C11.3166 -0.0976306 10.6834 -0.0976306 10.2929 0.292894C9.90237 0.683418 9.90237 1.31658 10.2929 1.70711L20.5858 12L10.2929 22.2929C9.90237 22.6834 9.90237 23.3166 10.2929 23.7071C10.6834 24.0976 11.3166 24.0976 11.7071 23.7071L22.7071 12.7071ZM13.7071 12.7071C13.8946 12.5196 14 12.2652 14 12C14 11.7348 13.8946 11.4804 13.7071 11.2929L2.70711 0.292894C2.31658 -0.0976302 1.68342 -0.0976302 1.29289 0.292894C0.902369 0.683419 0.902369 1.31658 1.29289 1.70711L11.5858 12L1.29289 22.2929C0.90237 22.6834 0.90237 23.3166 1.29289 23.7071C1.68342 24.0976 2.31658 24.0976 2.70711 23.7071L13.7071 12.7071Z" fill="#fff"/></svg></button>',
    responsive: [
      {
        breakpoint: 767,
        settings: {
          slidesToShow: 1,
        }
      }
    ]
  });
  // card slider 
  $('.logo-slider-main').slick({
    dots: false,
    infinite: true,
    speed: 800,
    slidesToShow: 6,
    slidesToScroll: 1,
    swipeToSlide: true,
    prevArrow: '<button class="slide-arrow slick-prev"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M22.7071 12.7071C22.8946 12.5196 23 12.2652 23 12C23 11.7348 22.8946 11.4804 22.7071 11.2929L11.7071 0.292894C11.3166 -0.0976306 10.6834 -0.0976306 10.2929 0.292894C9.90237 0.683418 9.90237 1.31658 10.2929 1.70711L20.5858 12L10.2929 22.2929C9.90237 22.6834 9.90237 23.3166 10.2929 23.7071C10.6834 24.0976 11.3166 24.0976 11.7071 23.7071L22.7071 12.7071ZM13.7071 12.7071C13.8946 12.5196 14 12.2652 14 12C14 11.7348 13.8946 11.4804 13.7071 11.2929L2.70711 0.292894C2.31658 -0.0976302 1.68342 -0.0976302 1.29289 0.292894C0.902369 0.683419 0.902369 1.31658 1.29289 1.70711L11.5858 12L1.29289 22.2929C0.90237 22.6834 0.90237 23.3166 1.29289 23.7071C1.68342 24.0976 2.31658 24.0976 2.70711 23.7071L13.7071 12.7071Z" fill="#fff"/></svg></button>',
    nextArrow: '<button class="slide-arrow slick-next"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M22.7071 12.7071C22.8946 12.5196 23 12.2652 23 12C23 11.7348 22.8946 11.4804 22.7071 11.2929L11.7071 0.292894C11.3166 -0.0976306 10.6834 -0.0976306 10.2929 0.292894C9.90237 0.683418 9.90237 1.31658 10.2929 1.70711L20.5858 12L10.2929 22.2929C9.90237 22.6834 9.90237 23.3166 10.2929 23.7071C10.6834 24.0976 11.3166 24.0976 11.7071 23.7071L22.7071 12.7071ZM13.7071 12.7071C13.8946 12.5196 14 12.2652 14 12C14 11.7348 13.8946 11.4804 13.7071 11.2929L2.70711 0.292894C2.31658 -0.0976302 1.68342 -0.0976302 1.29289 0.292894C0.902369 0.683419 0.902369 1.31658 1.29289 1.70711L11.5858 12L1.29289 22.2929C0.90237 22.6834 0.90237 23.3166 1.29289 23.7071C1.68342 24.0976 2.31658 24.0976 2.70711 23.7071L13.7071 12.7071Z" fill="#fff"/></svg></button>',
    responsive: [
      {
        breakpoint: 991,
        settings: {
          slidesToShow: 4,
        }
      },
      {
        breakpoint: 767,
        settings: {
          slidesToShow: 3,
        }
      },
      {
        breakpoint: 574,
        settings: {
          slidesToShow: 2,
        }
      },
      {
        breakpoint: 425,
        settings: {
          slidesToShow: 1,
        }
      }
    ]
  });
  // testimonials slider 
  $('.testi-main').slick({
    dots: false,
    infinite: true,
    speed: 800,
    slidesToShow: 2,
    slidesToScroll: 1,
    swipeToSlide: true,
    prevArrow: '<button class="slide-arrow slick-prev"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M22.7071 12.7071C22.8946 12.5196 23 12.2652 23 12C23 11.7348 22.8946 11.4804 22.7071 11.2929L11.7071 0.292894C11.3166 -0.0976306 10.6834 -0.0976306 10.2929 0.292894C9.90237 0.683418 9.90237 1.31658 10.2929 1.70711L20.5858 12L10.2929 22.2929C9.90237 22.6834 9.90237 23.3166 10.2929 23.7071C10.6834 24.0976 11.3166 24.0976 11.7071 23.7071L22.7071 12.7071ZM13.7071 12.7071C13.8946 12.5196 14 12.2652 14 12C14 11.7348 13.8946 11.4804 13.7071 11.2929L2.70711 0.292894C2.31658 -0.0976302 1.68342 -0.0976302 1.29289 0.292894C0.902369 0.683419 0.902369 1.31658 1.29289 1.70711L11.5858 12L1.29289 22.2929C0.90237 22.6834 0.90237 23.3166 1.29289 23.7071C1.68342 24.0976 2.31658 24.0976 2.70711 23.7071L13.7071 12.7071Z" fill="#fff"/></svg></button>',
    nextArrow: '<button class="slide-arrow slick-next"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M22.7071 12.7071C22.8946 12.5196 23 12.2652 23 12C23 11.7348 22.8946 11.4804 22.7071 11.2929L11.7071 0.292894C11.3166 -0.0976306 10.6834 -0.0976306 10.2929 0.292894C9.90237 0.683418 9.90237 1.31658 10.2929 1.70711L20.5858 12L10.2929 22.2929C9.90237 22.6834 9.90237 23.3166 10.2929 23.7071C10.6834 24.0976 11.3166 24.0976 11.7071 23.7071L22.7071 12.7071ZM13.7071 12.7071C13.8946 12.5196 14 12.2652 14 12C14 11.7348 13.8946 11.4804 13.7071 11.2929L2.70711 0.292894C2.31658 -0.0976302 1.68342 -0.0976302 1.29289 0.292894C0.902369 0.683419 0.902369 1.31658 1.29289 1.70711L11.5858 12L1.29289 22.2929C0.90237 22.6834 0.90237 23.3166 1.29289 23.7071C1.68342 24.0976 2.31658 24.0976 2.70711 23.7071L13.7071 12.7071Z" fill="#fff"/></svg></button>',
    responsive: [
      {
        breakpoint: 575,
        settings: {
          slidesToShow: 1,
        }
      }
    ]
  });
  // blog slider 
  $('.main-blog').slick({
    dots: false,
    infinite: true,
    speed: 800,
    slidesToShow: 4,
    slidesToScroll: 1,
    swipeToSlide: true,
    centerMode: false,
    prevArrow: '<button class="slide-arrow slick-prev"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M22.7071 12.7071C22.8946 12.5196 23 12.2652 23 12C23 11.7348 22.8946 11.4804 22.7071 11.2929L11.7071 0.292894C11.3166 -0.0976306 10.6834 -0.0976306 10.2929 0.292894C9.90237 0.683418 9.90237 1.31658 10.2929 1.70711L20.5858 12L10.2929 22.2929C9.90237 22.6834 9.90237 23.3166 10.2929 23.7071C10.6834 24.0976 11.3166 24.0976 11.7071 23.7071L22.7071 12.7071ZM13.7071 12.7071C13.8946 12.5196 14 12.2652 14 12C14 11.7348 13.8946 11.4804 13.7071 11.2929L2.70711 0.292894C2.31658 -0.0976302 1.68342 -0.0976302 1.29289 0.292894C0.902369 0.683419 0.902369 1.31658 1.29289 1.70711L11.5858 12L1.29289 22.2929C0.90237 22.6834 0.90237 23.3166 1.29289 23.7071C1.68342 24.0976 2.31658 24.0976 2.70711 23.7071L13.7071 12.7071Z" fill="#fff"/></svg></button>',
    nextArrow: '<button class="slide-arrow slick-next"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M22.7071 12.7071C22.8946 12.5196 23 12.2652 23 12C23 11.7348 22.8946 11.4804 22.7071 11.2929L11.7071 0.292894C11.3166 -0.0976306 10.6834 -0.0976306 10.2929 0.292894C9.90237 0.683418 9.90237 1.31658 10.2929 1.70711L20.5858 12L10.2929 22.2929C9.90237 22.6834 9.90237 23.3166 10.2929 23.7071C10.6834 24.0976 11.3166 24.0976 11.7071 23.7071L22.7071 12.7071ZM13.7071 12.7071C13.8946 12.5196 14 12.2652 14 12C14 11.7348 13.8946 11.4804 13.7071 11.2929L2.70711 0.292894C2.31658 -0.0976302 1.68342 -0.0976302 1.29289 0.292894C0.902369 0.683419 0.902369 1.31658 1.29289 1.70711L11.5858 12L1.29289 22.2929C0.90237 22.6834 0.90237 23.3166 1.29289 23.7071C1.68342 24.0976 2.31658 24.0976 2.70711 23.7071L13.7071 12.7071Z" fill="#fff"/></svg></button>',
    responsive: [
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: 3,
        }
      },
      {
        breakpoint: 800,
        settings: {
          slidesToShow: 2,
        }
      },
      {
        breakpoint: 575,
        settings: {
          slidesToShow: 1,
        }
      }
    ]
  });
  // product slider 
  $('.product-main-slider').slick({
    slidesToShow: 1,
    slidesToScroll: 1,
    arrows: true,
    dots: false,
    infinite: true,
    speed: 1500,
    loop: true,
    asNavFor: '.product-thumb-slider',
    autoplay: false,
    prevArrow: '<button class="slide-arrow slick-prev"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M22.7071 12.7071C22.8946 12.5196 23 12.2652 23 12C23 11.7348 22.8946 11.4804 22.7071 11.2929L11.7071 0.292894C11.3166 -0.0976306 10.6834 -0.0976306 10.2929 0.292894C9.90237 0.683418 9.90237 1.31658 10.2929 1.70711L20.5858 12L10.2929 22.2929C9.90237 22.6834 9.90237 23.3166 10.2929 23.7071C10.6834 24.0976 11.3166 24.0976 11.7071 23.7071L22.7071 12.7071ZM13.7071 12.7071C13.8946 12.5196 14 12.2652 14 12C14 11.7348 13.8946 11.4804 13.7071 11.2929L2.70711 0.292894C2.31658 -0.0976302 1.68342 -0.0976302 1.29289 0.292894C0.902369 0.683419 0.902369 1.31658 1.29289 1.70711L11.5858 12L1.29289 22.2929C0.90237 22.6834 0.90237 23.3166 1.29289 23.7071C1.68342 24.0976 2.31658 24.0976 2.70711 23.7071L13.7071 12.7071Z" fill="#fff"/></svg></button>',
    nextArrow: '<button class="slide-arrow slick-next"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M22.7071 12.7071C22.8946 12.5196 23 12.2652 23 12C23 11.7348 22.8946 11.4804 22.7071 11.2929L11.7071 0.292894C11.3166 -0.0976306 10.6834 -0.0976306 10.2929 0.292894C9.90237 0.683418 9.90237 1.31658 10.2929 1.70711L20.5858 12L10.2929 22.2929C9.90237 22.6834 9.90237 23.3166 10.2929 23.7071C10.6834 24.0976 11.3166 24.0976 11.7071 23.7071L22.7071 12.7071ZM13.7071 12.7071C13.8946 12.5196 14 12.2652 14 12C14 11.7348 13.8946 11.4804 13.7071 11.2929L2.70711 0.292894C2.31658 -0.0976302 1.68342 -0.0976302 1.29289 0.292894C0.902369 0.683419 0.902369 1.31658 1.29289 1.70711L11.5858 12L1.29289 22.2929C0.90237 22.6834 0.90237 23.3166 1.29289 23.7071C1.68342 24.0976 2.31658 24.0976 2.70711 23.7071L13.7071 12.7071Z" fill="#fff"/></svg></button>',
    responsive: [{
      breakpoint: 576,
      settings: {
        arrows: false,
        dots: false,
      }
    }]
  });
  $('.product-thumb-slider').slick({
    slidesToShow: 5,
    arrows: false,
    asNavFor: '.product-main-slider',
    dots: false,
    speed: 1500,
    slidesToScroll: 1,
    touchMove: true,
    focusOnSelect: true,
    loop: true,
    infinite: true,
    prevArrow: '<button class="slide-arrow slick-prev"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M22.7071 12.7071C22.8946 12.5196 23 12.2652 23 12C23 11.7348 22.8946 11.4804 22.7071 11.2929L11.7071 0.292894C11.3166 -0.0976306 10.6834 -0.0976306 10.2929 0.292894C9.90237 0.683418 9.90237 1.31658 10.2929 1.70711L20.5858 12L10.2929 22.2929C9.90237 22.6834 9.90237 23.3166 10.2929 23.7071C10.6834 24.0976 11.3166 24.0976 11.7071 23.7071L22.7071 12.7071ZM13.7071 12.7071C13.8946 12.5196 14 12.2652 14 12C14 11.7348 13.8946 11.4804 13.7071 11.2929L2.70711 0.292894C2.31658 -0.0976302 1.68342 -0.0976302 1.29289 0.292894C0.902369 0.683419 0.902369 1.31658 1.29289 1.70711L11.5858 12L1.29289 22.2929C0.90237 22.6834 0.90237 23.3166 1.29289 23.7071C1.68342 24.0976 2.31658 24.0976 2.70711 23.7071L13.7071 12.7071Z" fill="#fff"/></svg></button>',
    nextArrow: '<button class="slide-arrow slick-next"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M22.7071 12.7071C22.8946 12.5196 23 12.2652 23 12C23 11.7348 22.8946 11.4804 22.7071 11.2929L11.7071 0.292894C11.3166 -0.0976306 10.6834 -0.0976306 10.2929 0.292894C9.90237 0.683418 9.90237 1.31658 10.2929 1.70711L20.5858 12L10.2929 22.2929C9.90237 22.6834 9.90237 23.3166 10.2929 23.7071C10.6834 24.0976 11.3166 24.0976 11.7071 23.7071L22.7071 12.7071ZM13.7071 12.7071C13.8946 12.5196 14 12.2652 14 12C14 11.7348 13.8946 11.4804 13.7071 11.2929L2.70711 0.292894C2.31658 -0.0976302 1.68342 -0.0976302 1.29289 0.292894C0.902369 0.683419 0.902369 1.31658 1.29289 1.70711L11.5858 12L1.29289 22.2929C0.90237 22.6834 0.90237 23.3166 1.29289 23.7071C1.68342 24.0976 2.31658 24.0976 2.70711 23.7071L13.7071 12.7071Z" fill="#fff"/></svg></button>',
    responsive: [{
      breakpoint: 574,
      settings: {
        slidesToShow: 4,
        arrows: true,
      }
    }]
  });
      // Slick lightbox 
      if($('.lightbox').length>0) {
      $('.lightbox').slickLightbox({
          itemSelector: 'a.open-lightbox',
          caption: 'caption',
          prevArrow: '<button class="slide-arrow slick-prev"><svg viewBox="0 0 10 5"><path d="M2.37755e-08 2.57132C-3.38931e-06 2.7911 0.178166 2.96928 0.397953 2.96928L8.17233 2.9694L7.23718 3.87785C7.07954 4.031 7.07589 4.28295 7.22903 4.44059C7.38218 4.59824 7.63413 4.60189 7.79177 4.44874L9.43039 2.85691C9.50753 2.78197 9.55105 2.679 9.55105 2.57146C9.55105 2.46392 9.50753 2.36095 9.43039 2.28602L7.79177 0.69418C7.63413 0.541034 7.38218 0.544682 7.22903 0.702329C7.07589 0.859976 7.07954 1.11192 7.23718 1.26507L8.1723 2.17349L0.397965 2.17336C0.178179 2.17336 3.46059e-06 2.35153 2.37755e-08 2.57132Z"></path></svg></button>',
          nextArrow: '<button class="slide-arrow slick-next"><svg viewBox="0 0 10 5"><path d="M2.37755e-08 2.57132C-3.38931e-06 2.7911 0.178166 2.96928 0.397953 2.96928L8.17233 2.9694L7.23718 3.87785C7.07954 4.031 7.07589 4.28295 7.22903 4.44059C7.38218 4.59824 7.63413 4.60189 7.79177 4.44874L9.43039 2.85691C9.50753 2.78197 9.55105 2.679 9.55105 2.57146C9.55105 2.46392 9.50753 2.36095 9.43039 2.28602L7.79177 0.69418C7.63413 0.541034 7.38218 0.544682 7.22903 0.702329C7.07589 0.859976 7.07954 1.11192 7.23718 1.26507L8.1723 2.17349L0.397965 2.17336C0.178179 2.17336 3.46059e-06 2.35153 2.37755e-08 2.57132Z"></path></svg></button>',
          navigateByKeyboard: true,
          layouts: {
            closeButton: '<button class="close"><svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" viewBox="0 0 50 50" fill="none"><path d="M27.7618 25.0008L49.4275 3.33503C50.1903 2.57224 50.1903 1.33552 49.4275 0.572826C48.6647 -0.189868 47.428 -0.189965 46.6653 0.572826L24.9995 22.2386L3.33381 0.572826C2.57102 -0.189965 1.3343 -0.189965 0.571605 0.572826C-0.191089 1.33562 -0.191186 2.57233 0.571605 3.33503L22.2373 25.0007L0.571605 46.6665C-0.191186 47.4293 -0.191186 48.666 0.571605 49.4287C0.952952 49.81 1.45285 50.0007 1.95275 50.0007C2.45266 50.0007 2.95246 49.81 3.3339 49.4287L24.9995 27.763L46.6652 49.4287C47.0465 49.81 47.5464 50.0007 48.0463 50.0007C48.5462 50.0007 49.046 49.81 49.4275 49.4287C50.1903 48.6659 50.1903 47.4292 49.4275 46.6665L27.7618 25.0008Z" fill="white"></path></svg></button>'
          }
      });
  }
  // product desc slider 
  $('.desc-slider-main  ').slick({
    dots: false,
    infinite: true,
    speed: 800,
    slidesToShow: 1,
    slidesToScroll: 1,
    swipeToSlide: true,
    prevArrow: '<button class="slide-arrow slick-prev"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M22.7071 12.7071C22.8946 12.5196 23 12.2652 23 12C23 11.7348 22.8946 11.4804 22.7071 11.2929L11.7071 0.292894C11.3166 -0.0976306 10.6834 -0.0976306 10.2929 0.292894C9.90237 0.683418 9.90237 1.31658 10.2929 1.70711L20.5858 12L10.2929 22.2929C9.90237 22.6834 9.90237 23.3166 10.2929 23.7071C10.6834 24.0976 11.3166 24.0976 11.7071 23.7071L22.7071 12.7071ZM13.7071 12.7071C13.8946 12.5196 14 12.2652 14 12C14 11.7348 13.8946 11.4804 13.7071 11.2929L2.70711 0.292894C2.31658 -0.0976302 1.68342 -0.0976302 1.29289 0.292894C0.902369 0.683419 0.902369 1.31658 1.29289 1.70711L11.5858 12L1.29289 22.2929C0.90237 22.6834 0.90237 23.3166 1.29289 23.7071C1.68342 24.0976 2.31658 24.0976 2.70711 23.7071L13.7071 12.7071Z" fill="#fff"/></svg></button>',
    nextArrow: '<button class="slide-arrow slick-next"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M22.7071 12.7071C22.8946 12.5196 23 12.2652 23 12C23 11.7348 22.8946 11.4804 22.7071 11.2929L11.7071 0.292894C11.3166 -0.0976306 10.6834 -0.0976306 10.2929 0.292894C9.90237 0.683418 9.90237 1.31658 10.2929 1.70711L20.5858 12L10.2929 22.2929C9.90237 22.6834 9.90237 23.3166 10.2929 23.7071C10.6834 24.0976 11.3166 24.0976 11.7071 23.7071L22.7071 12.7071ZM13.7071 12.7071C13.8946 12.5196 14 12.2652 14 12C14 11.7348 13.8946 11.4804 13.7071 11.2929L2.70711 0.292894C2.31658 -0.0976302 1.68342 -0.0976302 1.29289 0.292894C0.902369 0.683419 0.902369 1.31658 1.29289 1.70711L11.5858 12L1.29289 22.2929C0.90237 22.6834 0.90237 23.3166 1.29289 23.7071C1.68342 24.0976 2.31658 24.0976 2.70711 23.7071L13.7071 12.7071Z" fill="#fff"/></svg></button>',
  });
  // tab js 
  $('ul.tabs li').click(function(){
    var $this = $(this);
    var $theTab = $(this).attr('data-tab');
    console.log($theTab);
    if($this.hasClass('active')){
    } else{
      $this.closest('.tabs-wrapper').find('ul.tabs li, .tabs-container .tab-content').removeClass('active');
      $('.tabs-container .tab-content[id="'+$theTab+'"], ul.tabs li[data-tab="'+$theTab+']').addClass('active');
    }
    $('.shop-protab-slider').slick('refresh');
    $('ul.tabs li').removeClass('active');
    $(this).addClass('active');
});

  if ($(".my-acc-column").length > 0) {
    jQuery(function ($) {
      var topMenuHeight = $("#daccount-nav").outerHeight();
      $("#account-nav").menuScroll(topMenuHeight);
      $(".account-list li:first-child").addClass("active");
    });
    // COPY THE FOLLOWING FUNCTION INTO ANY SCRIPTS
    jQuery.fn.extend({
      menuScroll: function (offset) {
        // Declare all global variables
        var topMenu = this;
        var topOffset = offset ? offset : 0;
        var menuItems = $(topMenu).find("a");
        var lastId;
        // Save all menu items into scrollItems array
        var scrollItems = $(menuItems).map(function () {
          var item = $($(this).attr("href"));
          if (item.length) {
            return item;
          }
        });
        // When the menu item is clicked, get the #id from the href value, then scroll to the #id element
        $(topMenu).on("click", "a", function (e) {
          var href = $(this).attr("href");
          var offsetTop = href === "#" ? 0 : $(href).offset().top - topOffset;
          function checkWidth() {
            var windowSize = $(window).width();
            if (windowSize <= 767) {
              $('html, body').stop().animate({
                scrollTop: offsetTop - 200
              }, 300);
            }
            else {
              $('html, body').stop().animate({
                scrollTop: offsetTop - 100
              }, 300);
            }
          }
          // Execute on load
          checkWidth();
          // Bind event listener
          $(window).resize(checkWidth);
          e.preventDefault();
        });
        // When page is scrolled
        $(window).scroll(function () {
          function checkWidth() {
            var windowSize = $(window).width();
            if (windowSize <= 767) {
              var nm = $("html").scrollTop();
              var nw = $("body").scrollTop();
              var fromTop = (nm > nw ? nm : nw) + topOffset;
              // When the page pass one #id section, return all passed sections to scrollItems and save them into new array current
              var current = $(scrollItems).map(function () {
                if ($(this).offset().top - 250 <= fromTop)
                  return this;
              });
              // Get the most recent passed section from current array
              current = current[current.length - 1];
              var id = current && current.length ? current[0].id : "";
              if (lastId !== id) {
                lastId = id;
                // Set/remove active class
                $(menuItems)
                  .parent().removeClass("active")
                  .end().filter("[href='#" + id + "']").parent().addClass("active");
              }
            }
            else {
              var nm = $("html").scrollTop();
              var nw = $("body").scrollTop();
              var fromTop = (nm > nw ? nm : nw) + topOffset;
              // When the page pass one #id section, return all passed sections to scrollItems and save them into new array current
              var current = $(scrollItems).map(function () {
                if ($(this).offset().top <= fromTop)
                  return this;
              });
              // Get the most recent passed section from current array
              current = current[current.length - 1];
              var id = current && current.length ? current[0].id : "";
              if (lastId !== id) {
                lastId = id;
                // Set/remove active class
                $(menuItems)
                  .parent().removeClass("active")
                  .end().filter("[href='#" + id + "']").parent().addClass("active");
              }
            }
          }
          // Execute on load
          checkWidth();
          // Bind event listener
          $(window).resize(checkWidth);
        });
      }
    });
  }
}); 
$(document).ready(function () {
  // home slider 
  var $slider = $('.home-main-slider');
  var $progressBar = $('.progress');
  var $progressBarLabel = $('.slider__label');
  $slider.on('beforeChange', function (event, slick, currentSlide, nextSlide) {
      var calc = ((nextSlide) / (slick.slideCount - 1)) * 100;
      $progressBar
          .css('background-size', calc + '% 100%')
          .attr('aria-valuenow', calc);
      $progressBarLabel.text(calc + '% completed');
  });
  $slider.slick({
      infinite: true,
      arrows:true,
      slidesToShow: 1,
      slidesToScroll: 1,
      speed: 800,
      fade: true,
      cssEase: 'linear',
      prevArrow: '<button class="slide-arrow slick-prev"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M22.7071 12.7071C22.8946 12.5196 23 12.2652 23 12C23 11.7348 22.8946 11.4804 22.7071 11.2929L11.7071 0.292894C11.3166 -0.0976306 10.6834 -0.0976306 10.2929 0.292894C9.90237 0.683418 9.90237 1.31658 10.2929 1.70711L20.5858 12L10.2929 22.2929C9.90237 22.6834 9.90237 23.3166 10.2929 23.7071C10.6834 24.0976 11.3166 24.0976 11.7071 23.7071L22.7071 12.7071ZM13.7071 12.7071C13.8946 12.5196 14 12.2652 14 12C14 11.7348 13.8946 11.4804 13.7071 11.2929L2.70711 0.292894C2.31658 -0.0976302 1.68342 -0.0976302 1.29289 0.292894C0.902369 0.683419 0.902369 1.31658 1.29289 1.70711L11.5858 12L1.29289 22.2929C0.90237 22.6834 0.90237 23.3166 1.29289 23.7071C1.68342 24.0976 2.31658 24.0976 2.70711 23.7071L13.7071 12.7071Z" fill="#fff"/></svg></button>',
      nextArrow: '<button class="slide-arrow slick-next"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M22.7071 12.7071C22.8946 12.5196 23 12.2652 23 12C23 11.7348 22.8946 11.4804 22.7071 11.2929L11.7071 0.292894C11.3166 -0.0976306 10.6834 -0.0976306 10.2929 0.292894C9.90237 0.683418 9.90237 1.31658 10.2929 1.70711L20.5858 12L10.2929 22.2929C9.90237 22.6834 9.90237 23.3166 10.2929 23.7071C10.6834 24.0976 11.3166 24.0976 11.7071 23.7071L22.7071 12.7071ZM13.7071 12.7071C13.8946 12.5196 14 12.2652 14 12C14 11.7348 13.8946 11.4804 13.7071 11.2929L2.70711 0.292894C2.31658 -0.0976302 1.68342 -0.0976302 1.29289 0.292894C0.902369 0.683419 0.902369 1.31658 1.29289 1.70711L11.5858 12L1.29289 22.2929C0.90237 22.6834 0.90237 23.3166 1.29289 23.7071C1.68342 24.0976 2.31658 24.0976 2.70711 23.7071L13.7071 12.7071Z" fill="#fff"/></svg></button>',
  });
});
var $slider = $('.home-main-slider');
if ($slider.length) {
  var currentSlide;
  var slidesCount;
  var sliderCounter = document.createElement('div');
  sliderCounter.classList.add('slider__counter');
  var updateSliderCounter = function (slick, currentIndex) {
      currentSlide = slick.slickCurrentSlide() + 1;
      slidesCount = slick.slideCount;
      $(sliderCounter).text('0' + currentSlide + '-' + '0' + slidesCount)
  };
  $slider.on('init', function (event, slick) {
      $slider.append(sliderCounter);
      updateSliderCounter(slick);
  });
  $slider.on('afterChange', function (event, slick, currentSlide) {
      updateSliderCounter(slick, currentSlide);
  });
}
var $status = $('.pagingInfo');
var $slickElement = $('.slideshow');
$slickElement.on('init reInit afterChange', function (event, slick, currentSlide, nextSlide) {
  //currentSlide is undefined on init -- set it to 0 in this case (currentSlide is 0 based)
  var i = (currentSlide ? currentSlide : 0) + 1;
  $status.text(i + '/' + slick.slideCount);
}); 