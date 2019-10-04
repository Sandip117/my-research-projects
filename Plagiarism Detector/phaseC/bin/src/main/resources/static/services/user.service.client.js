(function(){
    angular
        .module("UserDemo")
        .factory("UserService",UserService);
    
    function UserService($http) {
    	
    	
    	var api = {
            "createUser":createUser,
            "updateUser": updateUser,
            "findUserByCredentials": findUserByCredentials,
            "findUserById": findUserById,
            "findUserByEmailId": findUserByEmailId,
            "deleteUser":deleteUser,
            "logout":logout
        };
        return api;

        function deleteUser(userId) {
            return $http.delete('/api/user/'+userId);
        }

        function createUser(user) {
        return $http.post("/api/user", user);
        }

        function findUserByEmailId(emailId) {
        return $http.get("/api/usercred?emailId="+emailId);
        }

        function findUserByCredentials(emailId, password) {
            return $http.get("/api/usercred?emailId="+emailId+"&password="+password);
        }

        function updateUser(userId, newUser) {
            return $http.put("/api/user/"+userId, newUser);
        }

        function findUserById(uid) {
            return $http.get("/api/user/"+uid);
        }

        function logout() {
            var url = "/api/logout";
            return $http.post(url)
                .then(function (response) {
                    return response.data;
                })
        }
        
    }
})();
