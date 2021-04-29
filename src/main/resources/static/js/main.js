// Отправка задачи
$('#offer_task').on('click', function () {
  if (checkData() === false) {
    return;
  }

  let difficult = $('#difficult').val();

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

  $.ajax({
    type: 'POST',
    url: '/offer_task',
    contentType: "application/json",
    data: JSON.stringify({
      shortDescription: $('#short').val(),
      longDescription: $('#long').val(),
      difficult: difficult,
      startCode: startEditor.getValue(),
      testCode: testEditor.getValue()
    }),

    success: function (data) {
      console.log(data);
      swal({
        title: "Успех",
        text: "Ваша задача успешно отправлена...",
        icon: "success",
        className: "alert",
        button: {
          className: "alert_btn"
        }
      })

      setTimeout(function () {
        document.location.href = "/";
      }, 2000);
    },

    error: function (data) {
      console.log(data)
      swal({
        title: "Ошибка",
        text: data.responseText,
        icon: "error",
        className: "alert",
        button: {
          className: "alert_btn"
        }
      })
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

// Одобрить задачу
$('#accept').on('click', function () {
  if (checkData() === false) {
    return;
  }

  swal({
    title: "Уверены?",
    text: "После одобрения задачу нельзя будет удалить",
    icon: "warning",
    buttons: true,
    dangerMode: true,
  })
      .then((willDelete) => {
        if (willDelete) {
          $.ajax({
            type: "POST",
            url: "/accept",

            success: function (data) {
              swal("Отлично! Задача успешно одобрена!", {
                icon: "success",
              });

              setTimeout(function () {
                document.location.href = "/";
              }, 2000)
            }
          })
        }
      });

});

// Отклонить задачу
$('#decline').on('click', function () {

});

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

function moderation(element) {
  console.log(element.target.innerText);

  $.ajax({
    type: 'POST',
    url: '/select_moderation',
    data: {
      shortDescription: element.target.innerText
    },

    success: function (data) {
      document.location.href = "/moderation"
    }

  })
}

function checkData() {
  let shortDescription  = $('#short').val();
  let longDescription   = $('#long').val();
  let difficult         = $('#difficult').val();
  let startCode         = startEditor.getValue();
  let testCode          = testEditor.getValue();

  let startClassName    = $('#sc_name').val();
  let testClassName     = $('#tc_name').val();

  if ((startClassName !== undefined && testClassName !== undefined) && (startClassName.length < 1 || testClassName.length < 1)) {
    swal({
      title: "Ошибка",
      text: "Проверьте, всё ли вы заполнили",
      icon: "error",
      className: "alert",
      button: {
        className: "alert_btn"
      }}
    );

    return false;
  }

  if (shortDescription.length < 3 || longDescription.length < 10 || difficult === 0 || startCode.length < 10 || testCode.length < 10) {
    swal({
      title: "Ошибка",
      text: "Проверьте, всё ли вы заполнили",
      icon: "error",
      className: "alert",
      button: {
        className: "alert_btn"
      }}
    );

    return false;
  }

  return true;
}