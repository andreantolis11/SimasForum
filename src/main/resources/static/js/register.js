var password = document.getElementById("password"), conf_password = document.getElementById("conf_password");

function validatePassword() {
    if (password.value != conf_password.value) {
        conf_password.setCustomValidity("Passwords Don't Match");
    } else {
        conf_password.setCustomValidity('');
    }
}

password.onchange = validatePassword;
conf_password.onkeyup = validatePassword;