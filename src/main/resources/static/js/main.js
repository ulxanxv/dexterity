let typed = new Typed('.result', {

  strings: ["Все тесты^1000 успешно пройдены!", "..."],
  typeSpeed: 20,
  startDelay: 100,
  backDelay: 1000,
  showCursor: false,

  callback: function () {
    $('.ex_result').css('background-color', 'green');
  }

})

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
           $('#long_description').text(data.longDescription);
         }
       }
   })

});

$('#run_code').on('click', function () {

  $.ajax({
    type: 'GET',
    url: '/run_code',
    data: {
      code: editor.getValue(),
      taskName: $('#e_dshort').text()
    },

    success: function (data) {
      if (data.result === 'ok') {
        $('.ex_result').css('color', 'greed');
        let typed = new Typed('.result', {
          strings: ["Все тесты успешно пройдены!"]
        })
      } else {
        $('.ex_result').css('color', 'red');
        let typed = new Typed('.result', {
          strings: ["Что-то не так :("]
        })
      }
    }
  })

});