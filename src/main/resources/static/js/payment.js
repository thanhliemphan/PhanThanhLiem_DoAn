document.addEventListener("DOMContentLoaded", function () {
    const placeOrderButton = document.querySelector("#placeOrderButton");
    const paypalInput = document.querySelector("#paypal");

    placeOrderButton.addEventListener("click", function (event) {
        event.preventDefault();

        if (paypalInput.checked) {
            const form = document.querySelector("#paypalForm");
            const total = parseFloat(form.querySelector("[name='total']").value);
            const currency = form.querySelector("[name='currency']").value;
            const csrfToken = form.querySelector("[name='_csrf']").value;

            // Sử dụng URLSearchParams để tạo xâu dữ liệu cho form
            const formData = new URLSearchParams();
            formData.append("total", total);
            formData.append("currency", currency);


            fetch("/create-payment", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    "X-CSRF-TOKEN": csrfToken,
                },
                body: formData.toString(),
            })
                .then((response) => response.text()) // Nhận dữ liệu văn bản từ server
                .then((approvalUrl) => {
                    if (approvalUrl) {
                        window.location.href = approvalUrl; // Chuyển hướng đến URL chấp thuận
                    } else {
                        console.error("Failed to create PayPal payment.");
                    }
                })
                .catch((error) => {
                    console.error("Error creating PayPal payment:", error);
                });
        } else {
            window.location.href = "/save-order";
        }
    });
});
