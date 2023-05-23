let isCheckedId = false;

function idDuplicateCheck() {
    const userId = $('#userId').val();
    if(userId !== '') {
        $.ajax({
            url : "/user/duplicate",
            type : "get",
            data : {
                "userId" : userId
            },
            dataType: 'json',
            async: false
        }).done(function(data) {
            console.log(data);
            if(data.status == 'ok') {
                isCheckedId = true;
                alert("사용할수 있는 아이디 입니다.");
            }else if(data.status == 'no') {
                document.getElementById('feedBackId').style.display = 'block';
                document.getElementById('userId').style.backgroundImage = "none";
                document.getElementById('userId').style.borderColor = "red";
            }else {
                alert("알수없는 오류 발생 log 참조");
            }
        }).fail(function (request, status, error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        });
    }else {
        alert("아이디를 입력해 주세요.");
    }
}

function isValidated() {
    const password = $('#password').val();
    const passwdConfirm = $('#confirmPassword').val();
    const userName = $('#userName').val();
    if(password !== passwdConfirm) {
        document.getElementById('feedBack').style.display = 'block';
        document.getElementById('confirmPassword').style.backgroundImage = "none";
        document.getElementById('confirmPassword').style.borderColor = "red";
        return false;
    }if(userName === '' || password === '' || passwdConfirm === '') {
        return false
    }if(isCheckedId===false) {
        alert("아이디 중복확인 필요.");
        return false;
    }
    return true;
}

function frmSubmit() {
    const password = $('#password').val();
    const userName = $('#userName').val();
    const userId = $('#userId').val();
    const param = {
        "loginId" : userId,
        "name" : userName,
        "password" : password
    }
    if(isValidated()){

        $.ajax({
            url: '/user/insert',
            type: 'post',
            data: JSON.stringify(param),
            dataType: 'json',
            contentType : 'application/json'
        }).done(function(data) {
            if(data.result == 200) {
                alert('회원가입이 완료되었습니다.');
                location.href = "/user/login";
            }else {
                alert('회원가입이 실패했습니다.');
                return false;
            }
        }).fail(function (request, status, error) {
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        });
    }
}