document.addEventListener("DOMContentLoaded", function() {
    // Profile update form
    var updateProfileForm = document.getElementById("updateProfileForm");
    updateProfileForm.addEventListener("submit", function(event) {
        event.preventDefault();

        var formData = new FormData();
        formData.append("profilePicture", document.getElementById("profilePicture").files[0]);
        formData.append("bio", document.getElementById("bio").value);

        console.log(formData);

        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            console.log("ReadyState: " + xhr.readyState + ", Status: " + xhr.status);
            console.log(formData.get("bio"));
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    var responseJson = JSON.parse(xhr.responseText);
                    var updatedProfilePicturePath = responseJson.profilePicturePath;

                    updateProfilePicture(updatedProfilePicturePath);
                    
                    // Check if the bio value was actually updated before displaying
                    var updatedBioValue = document.getElementById("bio").value;
                    if (updatedBioValue.trim() !== "") {
                        var updatedBioDiv = document.getElementById("bioText");
                        updatedBioDiv.textContent = updatedBioValue; // Display the updated bio
                    }
                } else {
                    console.error("Error: " + xhr.statusText);
                }
            }
        };

        xhr.open("POST", "UpdateProfileServlet", true);
        xhr.send(formData);
    });
});


// After successfully updating profile details, update the profile picture
function updateProfilePicture(picturePath) {
    const profilePictureElement = document.getElementById("profile_Picture");
    console.log("Picture path: " + picturePath);
    profilePictureElement.src = "GetImageServlet?imageName=" + encodeURIComponent(picturePath);
}
