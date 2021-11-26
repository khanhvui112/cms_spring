$('#form-content-json').submit(event => {
    event.preventDefault();
    // call ajax to push data
    const title = $('#form-content-json input[name="title"]').val();
    const brief = $('#form-content-json textarea[name="brief"]').val();
    const content = $('#form-content-json textarea[name="content"]').val();

    const request = $.post({
        url: `${contextPath}/api/content-data-json`,
        //dataType: "json",
        data: {title, brief, content}
    });

    request.done(function() {
        alert("Add content success");
        $('#form-content-json').trigger("reset");
    });

    request.fail(function() {
        alert('Something wrong, please contact admin');
    });
});