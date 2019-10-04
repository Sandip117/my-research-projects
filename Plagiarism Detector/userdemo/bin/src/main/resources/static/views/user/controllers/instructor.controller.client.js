(function(){
	angular
	.module("UserDemo")
	.controller("instructorController", instructorController);

	function instructorController(UserService,InstructorService,$location, $routeParams, $scope) {
		$scope.name="sample";
		console.log($routeParams)
		var ecourse;
		var cid;
		var assignmentResponse;
		var vm = this;
		vm.assignmentsForCourse=[];
		vm.coursesForUser=[];
		vm.userId = $routeParams.uid;
		vm.getAllAssignmentsForCourseID = getAllAssignmentsForCourseID;
		vm.courseId = $routeParams.cid;
		vm.createCourse = createCourse;
		vm.addAssignment = addAssignment;	
		vm.deleteAssignment = deleteAssignment;
		vm.addCourse = addCourse;
		vm.getAllSemesters = getAllSemesters;
		vm.deleteCourse = deleteCourse;
		vm.editCourse = editCourse;
		vm.patchCourse = patchCourse;
		vm.patchAssignment = patchAssignment;
		vm.editAssignment = editAssignment;
		//vm.getStudents = getStudents;
		//vm.getReport = getReport;
		//vm.getStats = getStats;
		vm.getAllAssignmentsForCourseID = getAllAssignmentsForCourseID;
		vm.getAllCoursesForUserID = getAllCoursesForUserID;
		vm.getAssignmentById = getAssignmentById;
		getAllCoursesForUserID(vm.userId);
		getAllSemesters();
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
		$scope.selectedCourseForAssignment = {};
		if (this.courseId!=""){
			getCourseById(this.courseId);
		}

//		function addAssignment(){

//		$location.url("/instructor/addCourse");
//		InstructorService
//		.addAssignment(a)
//		.then(function (response){
//		$location.url("/instructor/addCourse");
//		}
//		,function (error){

//		})
//		}

		function createCourse(){
			console.log("create")
			$location.url("/instructor/"+this.userId+"/addCourse")

		}

		function addAssignment(assignment){
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
					}
					,function (error){
						alert(error);
					})
				});

			});



		}

		function getAllAssignmentsForCourseID(course){	
			vm.thisCourse = course
			console.log(vm.thisCourse)
			InstructorService
			.getAllAssignments(vm.thisCourse.courseId)
			.then(function (response){
				$scope.assignmentlist = response.data;
				console.log(response.data);
			}
			,function (error){

			})
		}

		function getAllCoursesForUserID(userId){
			InstructorService
			.getAllCourses(userId)
			.then(function (response){
				var courses=response.data;

				console.log(response.data);
				$scope.courselist = response.data;
				console.log($scope.courselist);
				vm.coursesForUser=response.data;
			}
			,function (error){

			})
		}


		function deleteAssignment(aid){
			console.log(aid);
			var r = confirm("Are you sure?");
			if(r == true){
				InstructorService
				.deleteAssignment(aid,this.courseId)
				.then(function (response){
					for(x in vm.assignmentsForCourse){
						if(aid==vm.assignmentsForCourse[x].id){
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
					for(x in vm.coursesForUser){
						if(cid==vm.coursesForUser[x].id){
							vm.coursesForUser.splice(x,1);
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
			$location.url("/instructor/"+this.userId+"/editassignment/"+aid);	
		
		}

		function getAssignmentById(aid){
			
			InstructorService
			.getAssignmentById(aid)
			.then(function(response){
				this.assignmentResponse = response.data;
				$scope.selectedCourseForAssignment = this.assignmentResponse.course;
				$scope.assignmentName = this.assignmentResponse.assignmentName
				$scope.assignmentId = this.assignmentResponse.assignmentId;
				
//				InstructorService
//				.updateAssignment(assignmentResponse)
//				.then(function(resp){
//					
//				})
			}
			,function (error){

			})
		}

		function editCourse(cid){
			console.log(cid)
			console.log("Edit Course")
			getCourseById(cid);
			$location.url("/instructor/"+this.userId+"/editCourse/"+cid);	
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
							$location.url("/instructor/"+$routeParams.uid+"/addCourse")
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
					$location.url("/instructor/"+$routeParams.uid)
					})
				})
		}

	}

})();
