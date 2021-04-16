/* 카카오  API */
window.Kakao.init("2173bb391b0a91ded25693c6149528c0");

function kakaoLogin() {
    window.Kakao.Auth.login({
        scope: 'profile,account_email,gender',
        success: function (autObj) {
            console.log(authObj);
            window.kakao.API.rquest({
                url: '/v2/user/me',
                success: res => {
                    const kakao_account = res.kakao_account;
                    console.log(kakao_account);
                }
            });
        }
    });
}
/* 2173bb391b0a91ded25693c6149528c0  Java Script */


/*JS Alert 로그인  디자인 */
function Sing_UP() {
    Swal.fire({
        position: 'top-bottom',
        icon: 'success',
        title: 'Your work has been saved',
        showConfirmButton: false,
        timer: 1500
    })
}