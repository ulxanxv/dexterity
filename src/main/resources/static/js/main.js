// Получение подробного описания задачи
$('.tb_text').on('click', function (e) {

    $.ajax({
       type: 'GET',
       url: '/task',
       data: {
           short_description: e.currentTarget.innerText.replace(/\s+/g, ' ').trim()
       },

       success: function (data) {
         if (data.status === 'ok') {
           $('#long_description').text(data.taskInfo.longDescription);
         }
       }
   })

});

// Получение результатов компиляции
$('#run_code').on('click', function () {

  $('.result').text('');

  $.ajax({
    type: 'GET',
    url: '/run_code',
    data: {
      code: editor.getValue(),
      taskName: $('#e_dshort').text()
    },

    success: function (data) {
      if (data.status === 'ok') {
        $('.ex_result').css('color', 'green');
        let typed = new Typed('.result', {
          strings: ['Все тесты^1000 успешно пройдены'],
          startDelay: 0,
          typeSpeed: 20,
          showCursor: false,

          preStringTyped: function () {
            $('.ex_result').css('color', 'green');
          }
        })
      } else {
        $('.ex_result').css('color', 'red');
        let typed = new Typed('.result', {
          strings: ['Ошибка :: ^1000 ' + data.message],
          startDelay: 0,
          typeSpeed: 20,
          showCursor: false,

          preStringTyped: function () {
            $('.ex_result').css('color', 'red');
          }
        })
      }
    }
  })

});

function openCity(evt, cityName) {
  let i, tabContent, tabLinks;
  tabContent = $('.tabcontent');

  for (i = 0; i < tabContent.length; i++) {
    tabContent[i].style.display = "none";
  }

  tabLinks = $('.tablinks');
  for (i = 0; i < tabLinks.length; i++) {
    tabLinks[i].className = tabLinks[i].className.replace(" active", "");
  }

  document.getElementById(cityName).style.display = "block";
  evt.currentTarget.className += " active";
}