function closeModal(num) {
    document.getElementById('detailModal'+num).style.display = "none"
}

function detailTodo(num) {
    document.getElementById('detailModal' + num).style.display = 'flex';
}

function completeTodo(num) {
    const userNo = document.getElementById('userNo').value;
    $.ajax ({
        url: '/todo/complete',
        type: 'GET',
        data: {
            "userNo" : userNo,
            "todoNum" : num
        }, })
        .done(
            function() {
                document.getElementById('complete' + num).style.display = 'none';
                document.getElementById('text' + num).style.textDecoration = 'line-through';
                $("#todoList").load(location.href+" #todoList");
            });

}

function deleteTodo(num) {
    const userNo = document.getElementById('userNo').value;
    $.ajax ({
        url: '/todo/delete',
        type: 'GET',
        async: false,
        data: {
            "userNo" : userNo,
            "todoNum" : num
        }, })
        .done(
            function() {
                document.getElementById('item' + num).style.display = 'none';
            }).fail(function () {
        alert("can not delete error");
    });
}

function addTodo() {
    const item = document.getElementById('todoInput').value;
    const userNo = document.getElementById('userNo').value;
    const param = {"item": item, "userNo" : userNo}
    $.ajax({
        url: '/todo/insert',
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify(param),
        async: false,
        dataType: 'json'
    }).done(
        function() {
            $("#todoList").load(location.href+" #todoList");
        }).fail(
        function (resultMap) {
            alert(resultMap.msg);
        });
}