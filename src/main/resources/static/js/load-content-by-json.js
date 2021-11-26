$(document).ready(function() {
    readContent('');
});

$('#search-form').submit(event => {
    event.preventDefault();

    const keyword = $('input[name="keyword"]').val();
    readContent(keyword);
});

function readContent(keyword) {
    const request = $.ajax({
        url: `${contextPath}/api/content-data-json?keyword=${keyword}`,
        method: "GET"
    });

    request.done(function( content ) {
        $(".loading-container").hide();
        $("#table-content table tbody").html("");
        content.forEach(function (item, index) {
            $("#table-content table tbody").
                append(`<tr>
                            <td>${index + 1}</td>
                            <td>${item.title}</td>
                            <td>${item.brief}</td>
                            <td>${item.create_date}</td>
                        </tr>`);
        });
        window.history.pushState({}, 'Search result',
            keyword ? `view-content-json?keyword=${keyword}` : `view-content`);
    });

    request.fail(function() {
        $(".loading-container").hide();
        $("#table-content").html("<p>Something wrong, please contact admin</p>");
    });
}