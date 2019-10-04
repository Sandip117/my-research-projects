(function(){
	angular
	.module("UserDemo")
	.controller("registerController", registerController);

	function registerController(UserService,$location) {
		var vm = this;
		vm.register = register;
		function register(user) {
			console.log("in register");
			if(user==null)
			{
				vm.error = "input empty! Please fill username and password";
			}
			else {
				UserService
				.findUserByEmailId(user.emailId)
				.success(function (user) {
					console.log(user)
					vm.error = "sorry that username is taken"
				})
				.error(function(){
					if ((user.emailId).endsWith("@husky.neu.edu")){
						user.role = "Student";
					}
					else if ((user.emailId).endsWith("@northeastern.edu")){
						user.role = "Instructor";
					}
					else{
						vm.error = 'Incorrect EmailId!';
						$location.url('/login');
					}
					UserService
					.createUser(user)
					.success(function(user){
						console.log(user)
						$location.url('/login');

					})
					.error(function () {
						vm.error = 'sorry could not register';
					});
				});
			}}}
})();
