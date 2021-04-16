function button1_click() {
    Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Something went wrong!',
        footer: '<a href>Why do I have this issue?</a>'
    })
}

function button2_click() {
    Swal.fire(
        'Good job!',
        'Membership success',
        'success'
    )
}