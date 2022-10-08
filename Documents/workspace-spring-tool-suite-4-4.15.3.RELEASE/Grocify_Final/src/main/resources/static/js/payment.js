const paymentStart = () => {
	console.log("payment started..");
	let amount = $("#payment_field").val();
	console.log(amount);
	if (amount == "" || amount == null) {
		// alert("amount is required !!");
		swal("Failed !!", "amount is required !!", "error");
		return;
	}

	//code...
	// we will use ajax to send request to server to create order- jquery

	$.ajax({
		url: "/create_order",
		data: JSON.stringify({ amount: amount, info: "order_request" }),
		contentType: "application/json",
		type: "POST",
		dataType: "json",
		success: function(response) {
			//invoked when success
			console.log(response);
			if (response.status == "created") {
				//open payment form
				let options = {
					key: "rzp_test_bqeEqIY5RTum0f",
					amount: response.amount,
					currency: "INR",
					name: "Grocify",
					description: "Payment for groceries",
					image:
						"https://scuffedentertainment.com/wp-content/uploads/2021/03/healthiest-vegetables-df1cf550711076d052eaade12c38289a2637c38e546182d3c0136a90cb0bb0b3.jpg",
					order_id: response.id,
					handler: function(response) {
						console.log(response.razorpay_payment_id);
						console.log(response.razorpay_order_id);
						console.log(response.razorpay_signature);
						console.log("payment successful !!");
						// alert("congrats !! Payment successful !!");

						updatPaymentOnserver(
							response.razorpay_payment_id,
							response.razorpay_order_id,
							"paid")
						swal("Good job!", "congrats !! Payment successful !!", "success");
					},
					prefill: {
						name: "",
						email: "",
						contact: "",
					},

					notes: {
						address: "Grocify",
					},
					theme: {
						color: "#3399cc",
					},
				};

				let rzp = new Razorpay(options);

				rzp.on("payment.failed", function(response) {
					console.log(response.error.code);
					console.log(response.error.description);
					console.log(response.error.source);
					console.log(response.error.step);
					console.log(response.error.reason);
					console.log(response.error.metadata.order_id);
					console.log(response.error.metadata.payment_id);
					//alert("Oops payment failed !!");            
					swal("Failed !!", "Oops payment failed !!", "error");
				});

				rzp.open();
			}
		},
		error: function(error) {
			//invoked when error
			console.log(error);
			alert("something went wrong !!");
		},
	});
};


function updatPaymentOnserver(payment_id, order_id, status)
{
    $.ajax({
		url: "/update_order",
		data: JSON.stringify({ 
			payment_id: payment_id, 
			order_id:  order_id,
			status: status,
			 }),
		contentType: "application/json",
		type: "POST",
		dataType: "json",
		success: function(response){
			swal("Good job!","congrates !! payment successful !!","success");
		},
		error: function(error){
		   swal("failed !!","Your payment successful, but we did not capture it","error")
		},
});
}