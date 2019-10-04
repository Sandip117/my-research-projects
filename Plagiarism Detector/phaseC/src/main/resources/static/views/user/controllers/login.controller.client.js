(function(){
	angular
	.module("UserDemo")
	.controller("loginController", loginController);

	function loginController(UserService, InstructorService, MenuService, SubmissionService, $location, $scope, $rootScope, $window) {
		
		var vm = this;
		var googleUserRole = "";
		var googleUserEmail = "";
		var firstName = "";
		var lastName = "";
		vm.login = login;
		vm.register = register;
		$scope.login_error = "";
		$scope.reg_error = "";
		$scope.reg_succ = "";
		$scope.course_num = 0;
		$scope.assign_num = 0;
		$scope.rep_num = 0;
		$scope.courseCount = 0;
		$scope.assignCount = 0;
		
		var logged_id = $window.localStorage.getItem("sher_user_id");
		var logged_role = $window.localStorage.getItem("sher_user_role");
		if(logged_id){
			console.log('already logged in, redirecting..')
			$location.url('/'+logged_role+'/');
		}
		vm.onSignIn = onSignIn;
		gapi.load('auth2', function() {//load in the auth2 api's, without it gapi.auth2 will be undefined
			gapi.auth2.init(
					{
						client_id: '360591443014-895c59hru7tn8ppdo0fdjh0kplggvn4p.apps.googleusercontent.com'
					}
			);
			var GoogleAuth  = gapi.auth2.getAuthInstance();//get's a GoogleAuth instance with your client-id, needs to be called after gapi.auth2.init
			vm.onLogInButtonClick=function(){//add a function to the controller so ng-click can bind to it
				console.log("this "+GoogleAuth.isSignedIn.get());
				GoogleAuth.signIn().then(function(response){//request to sign in
					var profile = response.getBasicProfile();
					GoogleAuth.disconnect();
					console.log(profile);
					console.log(profile.getEmail());
					console.log(profile.getName());
					var email = profile.getEmail();
					if (email.endsWith("@husky.neu.edu")){
						googleUserRole = "Student";
					}
					else if (email.endsWith("@northeastern.edu")){
						googleUserRole = "Instructor";
					}
					else{
						$scope.login_error = "Please use your Northeastern ID to Sign In!";
					}
					googleUserEmail = email;
					firstName = profile.getName().split(" ")[0];
					lastName = profile.getName().split(" ")[1];
					UserService
					.findUserByEmailId(email)
					.success(function (user) {
						console.log(user+"inside");
						if((user.role) == "Student"){
							$window.localStorage.setItem("sher_user_role","student");
							$window.localStorage.setItem("sher_user_id", user.id);
							console.log(user);
							$rootScope.user_role = "student";
							$rootScope.user_id = user.id;
							$location.url('/student/');
						}
						if((user.role) == "Instructor"){
							$window.localStorage.setItem("sher_user_role","instructor");
							$window.localStorage.setItem("sher_user_id", user.id);
							$rootScope.user_role = "instructor";
							$rootScope.user_id = user.id;
							$location.url('/instructor/');
						}
						if((user.role) == "Admin"){
							$window.localStorage.setItem("sher_user_role","admin");
							$window.localStorage.setItem("sher_user_id", user.id);
							$rootScope.user_role = "admin";
							$rootScope.user_id = user.id;
							$location.url('/admin/'+user.id);
						}
					})
					.error(function () {
						console.log("check ...."+googleUserRole +" "+ googleUserEmail);
						UserService
						.createUser({"role":googleUserRole,"emailId":googleUserEmail,"firstName":firstName,"lastName":lastName})
						.success(function(user){
							console.log("inside create user"+user);
							if((user.role) == "Student"){
								$window.localStorage.setItem("sher_user_role","student");
								$window.localStorage.setItem("sher_user_id", user.id);
								$rootScope.user_role = "student";
								$rootScope.user_id = user.id;
								$location.url('/student/');
							}
							if((user.role) == "Instructor"){
								$window.localStorage.setItem("sher_user_role","instructor");
								$window.localStorage.setItem("sher_user_id", user.id);
								$rootScope.user_role = "instructor";
								$rootScope.user_id = user.id;
								$location.url('/instructor/');
							}
							if((user.role) == "Admin"){
								$window.localStorage.setItem("sher_user_role","admin");
								$window.localStorage.setItem("sher_user_id", user.id);
								$rootScope.user_role = "admin";
								$rootScope.user_id = user.id;
								$location.url('/admin/');
							}})
							.error(function(){
								
							});
					});
				});
			}
		});

		function onSignIn(googleUser) {
			var profile = googleUser.getBasicProfile();
			console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
			console.log('Name: ' + profile.getName());
			console.log('Image URL: ' + profile.getImageUrl());
			console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
		}


		function register(user) {
			console.log(user)
			if(user==undefined)
			{
				$scope.reg_error = "Please enter all details before Registration";
			}
			else {
				UserService
				.findUserByEmailId(user.emailId)
				.success(function (user) {
					console.log(user)
					$scope.reg_error = "Sorry, that username is taken!"
				})
				.error(function(){
					if ((user.emailId).endsWith("@husky.neu.edu")){
						user.role = "Student";
					}
					else if ((user.emailId).endsWith("@northeastern.edu")){
						user.role = "Instructor";
					}
					else{
						$scope.reg_error = 'Invalid email. Please use your valid northeastern credentials';
						return;
					}
					UserService
					.createUser(user)
					.success(function(user){
						$scope.reg_succ = "Successfully Registered!";
						$scope.reg_error = "";
						$scope.model = null;

					})
					.error(function () {
						$scope.reg_error = "ERROR! Can't Register";
					});
				});
			}
		}

		function login(user) {
			if(user==undefined)
			{
				$scope.login_error = "Kindly fill in all details before Login";
			}
			else {
				if ((user.emailId).endsWith("@husky.neu.edu") || (user.emailId).endsWith("@northeastern.edu")|| (user.emailId).endsWith("@admin.edu")){
					var promise = UserService.findUserByCredentials(user.emailId, user.password);
					promise
					.success(function (user) {
						
						$window.localStorage.setItem("sher_user_name", user.firstName)
						console.log(user.id)
						if((user.role) == "Student"){
							$window.localStorage.setItem("sher_user_role","student");
							$window.localStorage.setItem("sher_user_id", user.id);
							$location.url('/student/');
						}
						
						if((user.role) == "Instructor"){
							
							getInstructorHomeDetails(user);
//							$.when(getInstructorHomeDetails).then(handleInstructorRedirect(user));
								
						}
						
						if((user.role) == "Admin"){
							$window.localStorage.setItem("sher_user_role","admin");
							$window.localStorage.setItem("sher_user_id", user.id);
							$rootScope.user_role = "admin";
							$rootScope.user_id = user.id;
							$location.url('/admin/');
						}
					})
					.error(function (er) {
						$scope.login_error = "Invalid Login Credentials";
					});
				}
				else{
					$scope.login_error = "That doesn't look like a valid Northeastern Email ID";
				}
			}
		}
		
		function getInstructorHomeDetails(user){
			InstructorService
			.getAllCourses(user.id)
			.then(function (response){
				var courses=response.data;
				console.log("COURSESS")
				console.log(courses)
				var assignNum = [];
				MenuService.setMenu(courses);
				
				InstructorService.putAllCourses(courses.length);
				$window.localStorage.setItem("sher_menu",JSON.stringify(courses));

				courses.forEach(function (x){
					console.log(x)
					$scope.courseCount = $scope.courseCount + 1;
					handleCourses(user, x, assignNum, courses);
													
				});
			}
			,function (error){

			})

		}
		
		function handleCourses(user, x, assignNum, courses){
			InstructorService
			.getAllAssignments(x.courseId)
			.then(function(resp){
				var assignments = resp.data;
				if(assignments){
					assignNum.push(assignments);	
					console.log(assignNum)
				}
				$scope.assignCount = $scope.assignCount + 1;
				handleAssignments(user, x, assignNum, assignments, courses);
				
			})	
		}
		
		function handleAssignments(user, x,assignNum, assignments, courses){
			var reportNum = [];
			
			assignments.forEach(function(y){
				SubmissionService
				.getReportByAssignId(y.assignmentId)
				.then(function(resp){
					var reps = resp.data;
					if(reps){											
						reportNum.push(reps);
						console.log(reportNum)
						console.log("Leng")
						console.log(courses.length)
						console.log(courses.indexOf(x))
						console.log(courses.indexOf(x)==courses.length-1)
						console.log(assignments.indexOf(y)==assignments.length-1)
						if($scope.courseCount == courses.length){
							if($scope.assignCount = assignments.length){
								$scope.rep_num = reportNum;
								$scope.assign_num = assignNum;
								var repMerged = [].concat.apply([], reportNum);
								var asMerged = [].concat.apply([],assignNum);

								$window.localStorage.setItem("sher_reps",JSON.stringify(repMerged));
								$window.localStorage.setItem("sher_assign",JSON.stringify(asMerged));
								handleInstructorRedirect(user);
							}
						}
					}
					
					
				})
			});
		}
		
		function handleInstructorRedirect(user){
			console.log($scope.rep_num)
			InstructorService.putAllAssignmentsNo($scope.assign_num);
			InstructorService
			.putAllReportsNo($scope.rep_num);
			$window.localStorage.setItem("sher_user_role","instructor");
				$window.localStorage.setItem("sher_user_id", user.id);
				$location.url('/instructor/');		
		}
		
		function getAssignmentsByCourse(courseId){
			var assignments;
				InstructorService
				.getAllAssignments(courseId)
				.then(function(resp){
					console.log("ASSIGNMENTS")
					console.log(resp.data)
					assignments = resp.data;
				})
				
			return assignments;
		}
		
		function getReportsByAssignment(assignId){
			var reports;
			SubmissionService
			.getReportByAssignId(assignId)
			.then(function(resp){
				reports = resp.data;
			})
			return reports;
		}
	}
})();
