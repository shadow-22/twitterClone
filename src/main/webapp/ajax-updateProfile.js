document.addEventListener("DOMContentLoaded", function() {
    // Profile update form
    var updateProfileForm = document.getElementById("updateProfileForm");
    updateProfileForm.addEventListener("submit", function(event) {
        event.preventDefault();

        var formData = new FormData();
        formData.append("profilePicture", document.getElementById("profilePicture").files[0]);
        formData.append("bio", document.getElementById("bio").value);

        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    // Success: Handle UI update or refresh
                    // For example, refresh the page to see the updated profile
                    location.reload();
                } else {
                    console.error("Error: " + xhr.statusText);
                }
            }
        };

        xhr.open("POST", "UpdateProfileServlet", true);
        xhr.send(formData);
    });
});