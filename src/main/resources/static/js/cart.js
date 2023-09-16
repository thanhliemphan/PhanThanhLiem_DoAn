// $(document).ready(function() {
//     // Theo dõi sự kiện thay đổi số lượng sản phẩm
//     $('.qty').on('change', function() {
//         var productId = $(this).data('product-id');
//         var newQuantity = $(this).val();
//
//         // Thực hiện AJAX để cập nhật tổng giá sản phẩm
//         $.ajax({
//             type: 'POST',
//             url: '/update-cart', // Thay thế đúng đường dẫn của API cập nhật giỏ hàng của bạn
//             data: {
//                 id: productId,
//                 quantity: newQuantity,
//                 action: 'update'
//             },
//             success: function(data) {
//                 // Cập nhật tổng giá cho sản phẩm cụ thể
//                 var totalId = 'totalPrice-' + productId;
//                 $('#' + totalId).text('$' + data.totalPrice);
//             },
//             error: function() {
//                 alert('Có lỗi xảy ra khi cập nhật giỏ hàng.');
//             }
//         });
//     });
// });
