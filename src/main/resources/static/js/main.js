// Отправка задачи
$('#offer_task').on('click', function () {
  if (checkData() === false) {
    return;
  }

  $.ajax({
    type: 'POST',
    url: '/offer_task',
    contentType: "application/json",
    data: JSON.stringify({
      shortDescription: $('#short').val(),
      longDescription: $('#long').val(),
      difficult: getDifficult(),
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

function updateRatingTable() {

  swal({
    title: "Подтверждение",
    text: "Эта процедура может занимать большое количество времени. Вы уверены?",
    icon: "warning",
    buttons: true,
    dangerMode: true,
  }).then((willAccept) => {

    if (willAccept) {
      $.ajax({
        type: "PATCH",
        url: "/update_rating_table",

        success: function (data) {
          console.log(data)
        },

        error: function (data) {
          console.log(data);
        }
      })
    }

  });

}

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
      let $exResult = $('.ex_result');
      let $result   = $('.result');

      console.log(data.status)

      if (data.status === 'ok') {
        $exResult.css('color', 'green');

        swal({
          title: "Поздравляем!",
          text: data.message + ". Перенаправление на страницу рейтинга...",
          icon: "success",
          showCancelButton: false,
          showConfirmButton: false
        });

        setTimeout(function () {
          document.location.href = "/rating"
        }, 5000)

      } else {
        $exResult.css('color', 'red');
        // $result.text(data.message);

        swal({
          title: "Ошибка",
          text: data.message,
          icon: "error",
          className: "alert",
          button: {
            className: "alert_btn"
          }}
        );
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
    title: "Подтверждение",
    text: "Вы уверены в принятии данной задачи?",
    icon: "warning",
    buttons: true,
    dangerMode: true,
  }).then((willAccept) => {

    if (willAccept) {
      $.ajax({
        type: "POST",
        url: "/accept",
        contentType: "application/json",
        data: JSON.stringify({
          shortDescription: $('#short').val(),
          longDescription: $('#long').val(),
          difficult: getDifficult(),
          className: $('#sc_name').val(),
          testClassName: $('#tc_name').val(),
          startCode: startEditor.getValue(),
          testCode: testEditor.getValue()
        }),

        success: function (data) {
          swal("Отлично! Задача успешно одобрена!", {
            icon: "success",
          });

          setTimeout(function () {
            document.location.href = "/moderation_list";
          }, 2000)
        }
      })
    }

  });

});

// Отклонить задачу
$('#decline').on('click', function () {
  swal({
    title: "Подтверждение",
    text: "Вы уверены, что хотите отклонить данную задачу?",
    icon: "warning",
    buttons: true,
    dangerMode: true,
  }).then((willDecline) => {

    if (willDecline) {
      $.ajax({
        type: "POST",
        url: "/decline",

        success: function () {
          swal("Задача отклонена!", {
            icon: "error",
          });

          setTimeout(function () {
            document.location.href = "/moderation_list";
          }, 2000)
        }
      })
    }

  });
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

// Выбор задачи на сервере
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

function getDifficult() {
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

  return difficult;
}

// Подгрузка рейтинг-таблицы
function searchRatingList(element) {

  $.ajax({
    type: 'GET',
    url: '/task_rating_list',
    data: {
      shortDescription: element.target.innerText
    },

    success: function (data) {
      let $ratingTable = $('.rating')
      let ratingHtml = "";

      $ratingTable.empty()

      ratingHtml += "<table>" +
          "<tr>" +
            "<th>Место</th>" +
            "<th>Логин пользователя</th>" +
            "<th>Задача</th>" +
            "<th>Скорость (нс.)</th>" +
            "<th>Краткость (симв.)</th>" +
            "<th>Оценка</th>" +
          "</tr>";

      for (let i = 0; i < data.length; ++i) {
        let each =
            "<tr>" +
              "<td>" + (i + 1) + "</td>" +
              "<td>" + data[i].credential.login + "</td>" +
              "<td>" + data[i].task.shortDescription + "</td>" +
              "<td>" + data[i].rapidity + "</td>" +
              "<td>" + data[i].brevity + "</td>" +
              "<td>" + data[i].totalScore.toFixed(3) + "</td>" +
            "</tr>"

        ratingHtml += each;
      }

      ratingHtml += "</table>";

      $ratingTable.html(ratingHtml);
    }

  })

}

// Поиск задачи (rating.html)
$('#t_search').on('input', function () {

  $.ajax({
    type: 'GET',
    url: '/search_tasks',
    data: {
      query: $('#t_search').val()
    },

    success: function (data) {
      let $taskList       = $('.task_list');
      let taskListInHtml  = "";

      $taskList.empty();

      for (let i = 0; i < data.length; ++i) {
        let color =
            data[i].difficult === 1 ? "#006400" : data[i].difficult === 2 ? "#646200" : "#640000";

        let difficult =
            data[i].difficult === 1 ? "Легко" : data[i].difficult === 2 ? "Средне" : "Трудно";

        let task =
            "<div class='task'>" +
              "<a class='tb_text' onclick=\"searchRatingList(event)\">" + data[i].shortDescription + "</a>" +
              "<span style='color: " + color + "'>" + difficult + "</span>" +
            "</div>";

        taskListInHtml += task;
      }

      $taskList.html(taskListInHtml);
    }
  })

})