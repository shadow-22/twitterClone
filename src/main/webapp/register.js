document.addEventListener("DOMContentLoaded", function() {
    // This code will be executed when the DOM is fully loaded

    // Get references to the form and button elements
    var usernameInput = document.getElementById("username");
    var usernameFeedback = document.getElementById("usernameFeedback");

    // Add event listener to the username input
    usernameInput.addEventListener("input", checkUsernameAvailability);

    function checkUsernameAvailability() {
        // Clear previous warning message
        usernameFeedback.textContent = "";

        var enteredUsername = usernameInput.value;

        // Perform AJAX request to check username availability
        // ...
        // Handle the response and display a warning message if needed
        // ...
        
        $.ajax({
            url: "CheckUsernameServlet",
            data: { username: enteredUsername },
            success: function(response) {
                if (response.exists) {
                    usernameFeedback.textContent = "Username is already taken.";
                    usernameFeedback.style.display = "block";
                    console.log("Name is already taken.");
                } else {
                    usernameFeedback.style.display = "none";
                    console.log("Name is not taken.");
                }
            }
        });

    }
});