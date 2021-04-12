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

  $.ajax({
    type: 'GET',
    url: '/run_code',
    data: {
      code: editor.getValue(),
      taskName: $('#e_dshort').text()
    },

    success: function (data) {
      console.log(data);
      if (jQuery.parseJSON(data.result).message === 'tests_passed') {
        $('.ex_result').css('color', 'green');
        let typed = new Typed('.result', {
          strings: ['Все тесты^1000 успешно пройдены', '...', ''],
          typeSpeed: 20,
          startDelay: 100,
          backDelay: 1000,
          showCursor: false,

          preStringTyped: function () {
            $('.ex_result').css('color', 'green');
          },
          callback: function () {
            $('.ex_result').typed('reset');
          }
        })
      } else {
        $('.ex_result').css('color', 'red');
        let typed = new Typed('.result', {
          strings: ['Ваш код не прошёл тесты :: ^1000 ' + jQuery.parseJSON(data.result).message, '...', ''],
          typeSpeed: 20,
          startDelay: 100,
          backDelay: 1000,
          showCursor: false,

          preStringTyped: function () {
            $('.ex_result').css('color', 'red');
          },
          callback: function () {
            $('.ex_result').typed('reset');
          }
        })
      }
    }
  })

});