$(document).ready(function() {
    readContent('');
});

$('#search-form').submit(event => {
    event.preventDefault();

    const keyword = $('input[name="keyword"]').val();
    readContent(keyword);
});

function readContent(keyword) {
    const searchParams = new URLSearchParams(window.location.search);
    let page = searchParams.get('page');
    page = page ? page : 1;
    const url = keyword ? `view-content?keyword=${keyword}` : `view-content`;

    $('.loading-container').show();
    const request = $.ajax({
        url: `${contextPath}/api/content-data?keyword=${keyword}&page=${page}`,
        method: "GET",
        dataType: "html"
    });

    request.done(function( content ) {
        $('.loading-container').hide();
        $("#table-content").html(content);
        window.history.pushState({}, 'Search result', url);
    });

    request.fail(function() {
        $('.loading-container').hide();
        $("#table-content").html("<p>Something wrong, please contact admin</p>");
    });
}