function validateCheck() {

    if($('#userId').val()=== '') {
        document.getElementById('validateId').style.display = 'block';
        return false;
    }else {
        document.getElementById('validateId').style.display = 'none';
    }
    if($('#userPassword').val()==='') {
        document.getElementById('validatePasswd').style.display = 'block';
        return false;
    }
    return true;
}

function login() {
    if(validateCheck()) {
        getRSAKey();
        securedData();
        $('#loginFrm').submit();
    }
}

function getRSAKey(){
    $.ajax({
        async : false, // 동기, 비동기 버튼 (false하면 동기)
        url : '/user/rsa-key',
        type : 'GET',
        dataType : 'json'
    }).done(function(data) {
        console.log('---------RSA공개키 가져오기 완료---------');
        console.log(data);
        $('#rsaPublicKeyModule').val(data.publicKeyModule);
        $('#rsaPublicKeyExponent').val(data.publicKeyExponent);
    }).fail(function(xhr, status, error) {
        console.log(error);
    });
}

function securedData() {
    const rsaKey = new RSAKey();
    rsaKey.setPublic($('#rsaPublicKeyModule').val(),$('#rsaPublicKeyExponent').val());
    $('#securedUserId').val(rsaKey.encrypt($('#userId').val()));
    $('#securedUserPassword').val(rsaKey.encrypt($('#userPassword').val()));
}