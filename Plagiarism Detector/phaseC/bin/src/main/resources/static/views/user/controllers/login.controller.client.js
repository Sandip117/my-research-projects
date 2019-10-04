(function(){
	angular
	.module("UserDemo")
	.controller("loginController", loginController);

	function loginController(UserService, $location) {
		
		var vm = this;
		vm.login = login;
		vm.register = register;

		function register(user) {
			console.log("in register");
			if(user==null)
			{
				vm.errorr = "input empty! Please fill username and password";
			}
			else {
				UserService
				.findUserByEmailId(user.emailId)
				.success(function (user) {
					console.log(user)
					vm.errorr = "sorry that username is taken"
				})
				.error(function(){
					if ((user.emailId).endsWith("@husky.neu.edu")){
						user.role = "Student";
					}
					else if ((user.emailId).endsWith("@northeastern.edu")){
						user.role = "Instructor";
					}
					else{
						vm.errorr = 'incorrect email id!';
						$location.url('/login');
						return;
					}
					UserService
					.createUser(user)
					.success(function(user){
						console.log(user)
						$location.url('/login');

					})
					.error(function () {
						vm.errorr = 'sorry could not register';
					});
				});
			}
		}

		function login(user) {
			if(user==null)
			{
				console.log("hello");
				vm.error = "fill in the username and password";
			}
			else {
				alert(user);
				if ((user.emailId).endsWith("@husky.neu.edu") || (user.emailId).endsWith("@northeastern.edu")|| (user.emailId).endsWith("@admin.edu")){
					var promise = UserService.findUserByCredentials(user.emailId, user.password);
					promise
					.success(function (user) {
						console.log(user.id)
						if((user.role) == "Student")
							$location.url('/student/'+user.id);
						if((user.role) == "Instructor")
							$location.url('/instructor/'+user.id);
						if((user.role) == "Admin")
							$location.url('/admin/'+user.id);
						else {
							vm.error = 'user not found';
						}
					})
					.error(function (er) {
						alert("testing_error");
						vm.error = "user not found";
					});
				}
				else{
					vm.error="invalid email id";
				}
			}
		}
	}
})();
