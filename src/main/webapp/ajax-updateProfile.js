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
                    var updatedBioDiv = document.getElementById("bioText");
                    updatedBioDiv.textContent = document.getElementById("bio").value; // Display the updated bio
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
    profilePictureElement.src = "GetImageServlet?imageName=" + encodeURIComponent(picturePath);
}
