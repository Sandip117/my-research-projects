(function(){
	angular
	.module("UserDemo")
	.controller("instructorController", instructorController);

	function instructorController(UserService,InstructorService,SubmissionService,MenuService,$location, $routeParams, $scope, $sce, $rootScope, $window) {

		$scope.usId = $window.localStorage.getItem("sher_user_id");
		console.log($scope.usId)
		var ecourse;
		var cid;
		var assignmentResponse;
		var vm = this;
		vm.assignmentsForCourse=[];
		vm.coursesForUser=[];
		vm.userId = $scope.usId;
		$scope.userId = vm.userId;
		vm.getAllAssignmentsForCourseID = getAllAssignmentsForCourseID;
		vm.courseId = $routeParams.cid;
		vm.createCourse = createCourse;
		vm.addAssignment = addAssignment;
		vm.deleteAssignment = deleteAssignment;
		vm.addCourse = addCourse;
		vm.deleteCourse = deleteCourse;
		vm.editCourse = editCourse;
		vm.patchCourse = patchCourse;
		vm.patchAssignment = patchAssignment;
		vm.editAssignment = editAssignment;
		vm.logout = logout;
		vm.getAllAssignmentsForCourseID = getAllAssignmentsForCourseID;
		vm.getAllCoursesForUserID = getAllCoursesForUserID;
		vm.getAssignmentById = getAssignmentById;
		vm.viewReport = viewReport;
		vm.loadReports = loadReports;
		vm.setThreshold = setThreshold;
		
		$scope.courselist= [];
		$scope.assignmentlist= [];
		$scope.semlist = [];
		$scope.uid=this.userId;
		$scope.c_title = "";
		$scope.c_id="";
		$scope.c_sem="";
		$scope.assignmentName="";	
		$scope.assignmentId="";
		$scope.enabledEdit = []
		$scope.allReportList = [];
		$scope.userList = [];
		$scope.assignName = "";
		$scope.selectedCourseForAssignment = {};
		$scope.reportStudent1 = "";
		$scope.reportStudent2 = "";
		$scope.reportPc = "";
		$scope.reportLinesCopied = "";
		$scope.reportFirstSide = "";
		$scope.reportSecondSide = "";
		$scope.threshList = [];
		$scope.amid = {};
		$scope.num_courses;
		$scope.num_assign;
		$scope.num_reps;
		$scope.all_assignments = [];
		
		$scope.selected_course = {};
		$scope.selected_assign = {};
		
		$scope.current_user = $window.localStorage.getItem("sher_user_id");
		$scope.current_role = $window.localStorage.getItem("sher_user_role");
		$scope.current_user_name = $window.localStorage.getItem("sher_user_name");

		$scope.num_courses;
		$scope.num_assign;
		$scope.num_reps;
		$scope.all_assignments = [];
		
		$scope.selected_course = {};
		$scope.selected_assign = {};
		
		$scope.current_user = $window.localStorage.getItem("sher_user_id");
		$scope.current_role = $window.localStorage.getItem("sher_user_role");
		$scope.current_user_name = $window.localStorage.getItem("sher_user_name");

		getAllSemesters();
		

		if(!$scope.current_user){
			window.location.replace("#/");
		} else {
			if ($scope.current_role != "instructor"){
				window.location.replace("#/");
			} else {
				
				if ($routeParams.cid){
					var sel_course = $routeParams.cid;
					startCourse(sel_course);					
				} else {
					startHome();
				}
			}
		}
		
		function startCourse(sel_course){
			$scope.courselist = JSON.parse($window.localStorage.getItem("sher_menu"));
			console.log($scope.courselist)
			console.log($scope.selected_course)

			$scope.selected_course = $scope.courselist.filter(function(x){
				return x.courseId == sel_course
			});
			
			$scope.selected_course = $scope.selected_course[0];
			console.log($scope.selected_course);

			if($scope.courselist != undefined){
					console.log(sel_course);
					getAllAssignmentsForCourseID($scope.selected_course);
				}
				else {
					getAllCoursesForUserID(vm.userId);
					getAllAssignmentsForCourseID($scope.selected_course);
				}	
			}
		
		function startHome(){
			$scope.courselist = JSON.parse($window.localStorage.getItem("sher_menu"));
			var c = JSON.parse($window.localStorage.getItem("sher_menu"));
			console.log($scope.courselist)
			$scope.num_courses = c.length;
			if ($scope.num_courses){
				$scope.all_assignments = JSON.parse($window.localStorage.getItem("sher_assign"));
				$scope.num_assign = $scope.all_assignments.length;
				$scope.num_reps = JSON.parse($window.localStorage.getItem("sher_reps")).length;
				startUp(c);
			} else {
				console.log("NO DICE")
				var menu = MenuService.getMenu();
				if (menu){
					$scope.courselist = menu;
				} else {
					getAllCoursesForUserID($scope.current_user);
					getInstructorHomeDetails();
				}

			}
		}
		
		function getInstructorHomeDetails(){
			
			console.log("IN HEREE!!")
				var assignNum = 0;
				var reportNum = 0;
								
				$scope.courselist.forEach(function (x){
					console.log(x)
					InstructorService
					.getAllAssignments(x.courseId)
					.then(function(resp){
						var assignments = resp.data;
						if(assignments){
							assignNum = assignNum + assignments.length;	
							console.log(assignNum)
						}
						assignments.forEach(function(y){
							SubmissionService
							.getReportByAssignId(y.assignmentId)
							.then(function(resp){
								var reps = resp.data;
								if(reps){											
									reportNum = reportNum + reps.length;
									console.log(reportNum)
									if($scope.courselist.indexOf(x)==$scope.courselist.length-1){
										if(assignments.indexOf(y)==assignments.length-1){
											$scope.num_assign = assignNum;
											$scope.num_reps = reportNum;
											InstructorService.putAllAssignmentsNo(assignNum);
											InstructorService.putAllReportsNo(reportNum);
										}
									}
								}
								
								
							})
						});
					})									
				});

		}
		
		function getAssignmentsByCourse(courseId){
			InstructorService
			.getAllAssignments(courseId)
			.then(function(resp){
				return resp.data;
			})
		}
	
		function getReportsByAssignment(assignId){
			SubmissionService
			.getReportByAssignId(assignId)
			.then(function(resp){
				return resp.data;
			})
		}

		function logout(){
			var auth2 = null;
			var googleUser = null;

			gapi.load('auth2', function() {//load in the auth2 api's, without it gapi.auth2 will be undefined
				gapi.auth2.init(
						{
							client_id: '360591443014-895c59hru7tn8ppdo0fdjh0kplggvn4p.apps.googleusercontent.com'
						}
				);

				auth2 = gapi.auth2.getAuthInstance();
				googleUser = auth2.currentUser.get();
				if(googleUser.isSignedIn()){
					auth2.signOut().then(function(){
						auth2.disconnect();
						auth2.currentUser.get().reloadAuthResponse();
					});
				}
			});
			var currentUrl = $window.location.href;

			$window.location.href="https://appengine.google.com/_ah/logout?continue=https://neuidmsso.neu.edu/logout.html?continue="+currentUrl;
			UserService.logout();
		}
		
		function startUp(c){
			console.log(c)
			if(c != undefined){
				var menu = JSON.parse($window.localStorage.getItem("sher_menu"));
				if (menu){
					$scope.courselist = menu;
				} else {
					getAllCoursesForUserID(vm.userId);				
				}	
			}
		}

//	function init(){
//		if($scope.assignmentList == undefined){
//			getAllAssignmentsForCourseID($scope.courselist[0]);
//		}
//	}

	function loadReports(assignment){
		$scope.selected_assign = assignment;

		getAllReports(assignment.assignmentId);
	}


		function loadReports(assignment){
			getAllReports(assignment.assignmentId);
		}

		function setThreshold(tVal){
			if(tVal!=null){

				if(tVal>100 || tVal<0 || tVal == ""){
					alert("Threshold value cannot be greater than 100 or less than 0!");
				} else {
					if($scope.allReportList.length>0){
						var allReps = $scope.allReportList;
						var newReps = [];

						for(var i=0; i<allReps.length;i++){
							if(allReps[i].plagiarismScore >= tVal){
								newReps.push(allReps[i]);
							}
						}

						console.log(newReps)
						$scope.threshList = newReps;

					}
				}

			}
		}

		function getAllReports(assignId){


		InstructorService
		.getAssignmentById(assignId)
		.then(function (resp){
			$scope.assignName = resp.data.assignmentName;
		});



			SubmissionService
			.getReportByAssignId(assignId)
			.then(function(resp){
				for (var i=0; i<resp.data.length; i++){
					console.log("resp data")
					console.log(resp.data[i])
					var u1_id = resp.data[i].user1Id;
					var u2_id = resp.data[i].user2Id;
					var mid = resp.data[i].mossId;
					console.log($scope.amid)
					getStudentNames(u1_id,u2_id,mid);

				}

				console.log("USER LIST")
				console.log($scope.userList)
				$scope.allReportList = resp.data;
				$scope.threshList = resp.data;


				console.log("REPORT LIST")
				console.log($scope.allReportList)
			});
		}

		function getStudentNames(u1_id,u2_id, mid){
			$scope.userList[mid] = {}

			UserService
			.findUserById(u1_id)
			.then(function (response){
				console.log("mid")
				console.log($scope.amid)
				$scope.userList[mid].user1Id = response.data;
			});
			UserService
			.findUserById(u2_id)
			.then(function (response){
				$scope.userList[mid].user2Id = response.data;
			});
		}

		$scope.trustSrc = function(src) {
			return $sce.trustAsResourceUrl(src);
		}

		function viewReport(report){
			console.log("report")
			console.log(report)
			$scope.reportStudent1 = $scope.userList[report.mossId].user1Id.emailId;
			$scope.reportStudent2 = $scope.userList[report.mossId].user2Id.emailId;
			$scope.reportPc = report.plagiarismScore;
			$scope.reportLinesCopied = report.linesCopied;
			var link = report.mossLink;
			var newLink = link.substring(0, link.length-5);
			var firstSide = newLink+"-0.html";
			var secondSide = newLink+"-1.html";
			console.log(firstSide)
			console.log(secondSide)
			$scope.reportRegScore = report.regressionScore;
			$scope.reportFirstSide = firstSide;
			$scope.reportSecondSide = secondSide;
		}

		function createCourse(){
			console.log("create")
			$location.url("/instructor/addCourse")

		}
		 
		$("#datetimepicker1").on("dp.change", function(e) {
			$scope.date = $("#datetimepicker1").val(); 
		});
		 
		function addAssignment(assignment){
			console.log($scope.date);
			console.log(assignment);
			//assignment['dueDate'] = $scope.date; 
			if(assignment){
				InstructorService
				.getCourseById(vm.courseId)
				.then(function (response){
					assignment.course = response.data
					InstructorService
					.addAssignment(assignment)

					.then(function (response){
						$location.url("/instructor/"+vm.userId);
					}
					,function (error){
						console.log("sfd");

					})
				}
				,function (error){
					console.log("sfd");

				})

			}
		}

		function addCourse(c){
			var semId = parseInt(c.semester);
			InstructorService
			.getSemesterById(semId)
			.success(function (sem){
				c.semester = sem;
				console.log(c)

				UserService
				.findUserById(vm.userId)
				.success(function (user) {
					c.faculty=user;
					console.log(c)

					InstructorService
					.addCourse(c)
					.then(function (response){
						console.log(c);
						$scope.courselist= getAllCoursesForUserID(vm.userId);
						MenuService.setMenu($scope.courselist);
						$window.localStorage.removeItem("sher_menu");
						$window.localStorage.setItem("sher_menu", JSON.stringify($scope.courselist));
					}
					,function (error){
						alert(error);
					})
				});

			});



		}

		function getAllAssignmentsForCourseID(course){
			$scope.selected_course = course;
			vm.thisCourse = course
			console.log(vm.thisCourse)
			InstructorService
			.getAllAssignments(vm.thisCourse.courseId)
			.then(function (response){
				$scope.assignmentlist = response.data;
				console.log(response.data);
				console.log("NEW")
				if(response.data[0] != undefined){
					getAllReports(response.data[0].assignmentId);
					$scope.selected_assign = response.data[0];
				} else {
					$scope.allReportList = [];
					$scope.threshList = [];
				}
			}
			,function (error){

			})
		}

		function getAllCoursesForUserID(userId){
			InstructorService
			.getAllCourses(userId)
			.then(function (response){
				var courses=response.data;
				$scope.courselist = response.data;
				MenuService.setMenu($scope.courselist);
				InstructorService.putAllCourses($scope.courselist);
				$scope.num_courses = $scope.courselist.length;
				

//				init();

			}
			,function (error){

			})
		}


		function deleteAssignment(assign){
			var r = confirm("Are you sure?");
			if(r == true){
				InstructorService
				.deleteAssignment(assign)
				.then(function (response){
					for(x in vm.assignmentsForCourse){
						if(assign.assignmentId==vm.assignmentsForCourse[x].id){
							vm.assignmentsForCourse.splice(x,1);
						}
						getAllAssignmentsForCourseID(vm.courseId)
						break;
					}
				}
				,function (error){
					vm.error="unable to delete";
				})
			}
		}

		function deleteCourse(cid){
			console.log(cid);
			var r = confirm("Are you sure?");
			if(r == true){
				InstructorService
				.deleteCourse(cid,this.userId)
				.then(function (response){
					for(x in $scope.courselist){
						if(cid==$scope.courselist[x].id){
							$scope.courselist.splice(x,1);
							$window.locationStorage.setItem("sher_menu", $scope.courselist);
						}
						getAllCoursesForUserID(vm.userId)
						break;
					}
				}
				,function (error){
					vm.error="unable to delete";
				})
			}
		}

		function getAllSemesters(){
			InstructorService
			.getAllSemesters()
			.then(function(response){
				console.log(response.data)
				$scope.semlist = response.data;
			}
			,function (error){

			})
		}
		
		function editAssignment(aid){

//			console.log(this.assignmentResponse)
			$location.url("/instructor/editassignment/"+aid);

		}

		function getAssignmentById(aid){

			InstructorService
			.getAssignmentById(aid)
			.then(function(response){
				this.assignmentResponse = response.data;
				$scope.selectedCourseForAssignment = this.assignmentResponse.course;
				$scope.assignmentName = this.assignmentResponse.assignmentName
				$scope.assignmentId = this.assignmentResponse.assignmentId;

			}
			,function (error){

			})
		}

		function editCourse(cid){
			console.log(cid)
			console.log("Edit Course")
			getCourseById(cid);
			$location.url("/instructor/editCourse/"+cid);
		}

		function getCourseById(cid){
			console.log(cid)
			InstructorService
			.getCourseById(cid)
			.then(function (response){
				this.ecourse=response.data;
				$scope.c_title = this.ecourse.courseName;
				$scope.c_id = this.ecourse.courseId;
				console.log(this.ecourse)
			}
			,function (error){

			})
		}

		function patchCourse(c){
			if(c!=null){
				if(c.semester!=null){
					var semId = parseInt(c.semester);

					InstructorService
					.getSemesterById(semId)
					.success(function (sem){
						this.ecourse.semester = sem;
						console.log(this.ecourse)

						if(c.courseName!=null){
							this.ecourse.courseName = c.courseName;
						}

						InstructorService
						.addCourse(this.ecourse)
						.then(function (response){
							console.log(this.userId)
							$location.url("/instructor/addCourse")
						}
						,function (error){
							alert(error);
						})

					});

				}

			}
		}

		function patchAssignment(a){
			a.assignmentId = $routeParams.aid
			InstructorService
			.getAssignmentById($routeParams.aid)
			.then(function(response){
				this.assignmentResponse = response.data;
				$scope.selectedCourseForAssignment = this.assignmentResponse.course;
				$scope.assignmentName = this.assignmentResponse.assignmentName
				$scope.assignmentId = this.assignmentResponse.assignmentId;
				a.course = $scope.selectedCourseForAssignment;
				InstructorService
				.updateAssignment(a)
				.then(function(resp){
					$location.url("/instructor/")
				})
			})
		}

	}

})();
