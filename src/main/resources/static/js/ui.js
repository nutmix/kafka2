$("#producer").submit(function(event) {

    /* stop form from submitting normally */
    event.preventDefault();

    /* get the action attribute from the <form action=""> element */
    var $form = $(this), url = "/produce"

    let data = {
        key: $('#key').val(),
        value: $('#value').val()
    }

    /* Send the data using post with element id name and name2*/
    var posting = $.post({
        type:"POST",
        dataType : "text",
        contentType: "application/json; charset=utf-8",
        url: url,
        data: JSON.stringify(data)
    });

    /* Alerts the results */
    posting.done(function(data) {
        $('#result').append("<p>SENT: " + data +"</p>");
    });
    posting.fail(function() {
        $('#result').append("<p>failed to send message</p>");
    });
});


setInterval(function()
{
    $.ajax({
        type:"get",
        url:"/consume",
        datatype:"text",
        success:function(data)
        {
            if ((data.key != null) || (data.value != null))
            {
                $('#result').append("<p>RECEIVED: " + JSON.stringify(data) + "</p>");
            }
        }
    });
}, 10000);//time in milliseconds