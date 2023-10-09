let star = document.querySelectorAll('input');
let showValue = document.querySelector('#rating');

for (let i = 0; i < star.length; i++) {
	star[i].addEventListener('click', function() {
		i = this.value;

		showValue.innerHTML = i;
	});
}