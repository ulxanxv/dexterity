// Отправка задачи
$('#offer_task').on('click', function () {

  let shortDescription = $('#short').val(),
      longDescription = $('#long').val(),
      difficult = $('#difficult').val(),
      startCode = startEditor.getValue(),
      testCode = testEditor.getValue();

  switch (difficult) {
    case "Легко":
      difficult = 1;
      break
    case "Средне":
      difficult = 2
      break
    case "Трудно":
      difficult = 3
      break
    default:
      difficult = 0
  }

  if (shortDescription.length < 3 || longDescription.length < 10 || difficult === 0 || startCode.length < 10 || testCode.length < 10) {
    swal({
      title: "Ошибка",
      text: "Проверьте, всё ли вы заполнили",
      icon: "error",
      className: "alert",
      button: {
        className: "alert_btn"
      }
    });

    return;
  }

  $.ajax({
    type: 'POST',
    url: '/offer_task',
    contentType: "application/json",
    data: JSON.stringify({
      shortDescription: shortDescription,
      longDescription: longDescription,
      difficult: difficult,
      startCode: startCode,
      testCode: testCode
    }),

    success: function (data) {
      console.log(data);
    }
  })

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
           $('#long_description').text(data.taskInfo.longDescription);
         }
       }
   })

})

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

})

function openTab(evt, tabName) {
  let i, tabContent, tabLinks;
  tabContent = $('.tabcontent');

  for (i = 0; i < tabContent.length; i++) {
    tabContent[i].style.display = "none";
  }

  tabLinks = $('.tablinks');
  for (i = 0; i < tabLinks.length; i++) {
    tabLinks[i].className = tabLinks[i].className.replace(" active", "");
  }

  document.getElementById(tabName).style.display = "block";
  evt.currentTarget.className += " active";
}