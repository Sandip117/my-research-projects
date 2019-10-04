(function(){
    angular
        .module("UserDemo")
        .factory("AdminService",AdminService);
    
   
    function AdminService($http) {
    	var api = {
            "getAllUsers":getAllUsers
        };
        return api;   
        function getAllUsers(){
        	return $http.get("/api/allUsers");
        }
       
        
        
    }
})();