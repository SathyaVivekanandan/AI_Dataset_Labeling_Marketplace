const API = "http://localhost:8080/api";

let me = JSON.parse(localStorage.getItem("labelai_user") || "null");

const $ = id => document.getElementById(id);


// ===============================
// ALERT MESSAGE
// ===============================
function alertMsg(msg, type = "success") {
    $("alert").innerHTML =
        `<div class="alert alert-${type}">${msg}</div>`;

    setTimeout(() => {
        $("alert").innerHTML = "";
    }, 3000);
}


// ===============================
// COMMON API REQUEST
// JWT TOKEN ADDED AUTOMATICALLY
// ===============================
async function req(url, opt = {}) {

    const headers = {
        "Content-Type": "application/json",
        ...(opt.headers || {})
    };

    // Add JWT token for authenticated requests
    if (me && me.token) {
        headers["Authorization"] = `Bearer ${me.token}`;
    }

    const response = await fetch(API + url, {
        ...opt,
        headers: headers
    });

    const data = await response.json().catch(() => ({}));

    if (!response.ok) {

        if (response.status === 401) {
            throw new Error("Session expired. Please login again.");
        }

        if (response.status === 403) {
            throw new Error("Access denied. Please login again.");
        }

        throw new Error(
            data.error ||
            data.message ||
            `Request failed (${response.status})`
        );
    }

    return data;
}
// ===============================
// SHOW LOGIN FORM
// ===============================
function showLogin() {

    $("loginForm").classList.remove("d-none");

    $("registerForm").classList.add("d-none");
}


// ===============================
// SHOW REGISTER FORM
// ===============================
function showRegister() {

    $("loginForm").classList.add("d-none");

    $("registerForm").classList.remove("d-none");
}

// ===============================
// REGISTER
// ===============================
async function register() {

    try {

        const data = await req("/auth/register", {
            method: "POST",

            body: JSON.stringify({
                name: $("rname").value,
                email: $("remail").value,
                password: $("rpassword").value,
                role: $("rrole").value
            })
        });

        alertMsg(
            data.message || "Registration successful. Please login."
        );

    } catch (e) {

        alertMsg(e.message, "danger");
    }
}


// ===============================
// LOGIN
// ===============================
async function login() {

    try {

        const loginData = await req("/auth/login", {
            method: "POST",

            body: JSON.stringify({
                email: $("lemail").value,
                password: $("lpassword").value
            })
        });

        // Save complete login response
        me = loginData;

        // Make sure JWT exists
        if (!me.token) {
            throw new Error("Login successful but JWT token was not received.");
        }

        localStorage.setItem(
            "labelai_user",
            JSON.stringify(me)
        );

        showApp();

    } catch (e) {

        alertMsg(e.message, "danger");
    }
}


// ===============================
// LOGOUT
// ===============================
function logout() {

    localStorage.removeItem("labelai_user");

    me = null;

    location.reload();
}


// ===============================
// LOAD DASHBOARD DATA
// ===============================
async function load() {

    try {

        const [
            datasets,
            projects,
            tasks,
            annotations,
            reviews,
            users
        ] = await Promise.all([

            req("/datasets"),

            req("/projects"),

            req("/tasks"),

            req("/annotations"),

            req("/reviews"),

            req("/auth/users")
        ]);


        // ===============================
        // DASHBOARD COUNTS
        // ===============================

        $("datasetCount").textContent = datasets.length;

        $("projectCount").textContent = projects.length;

        $("taskCount").textContent = tasks.length;

        $("reviewCount").textContent = reviews.length;


        // ===============================
        // DATASET DROPDOWN
        // ===============================

        $("pdataset").innerHTML =
            datasets.map(dataset =>
                `<option value="${dataset.id}">
                    ${dataset.title}
                </option>`
            ).join("");


        // ===============================
        // PROJECT DROPDOWN
        // ===============================

        $("tproject").innerHTML =
            projects.map(project =>
                `<option value="${project.id}">
                    ${project.projectName}
                </option>`
            ).join("");


        // ===============================
        // ANNOTATOR DROPDOWN
        // ===============================

        $("tannotator").innerHTML =
            users
                .filter(user => user.role === "ANNOTATOR")
                .map(user =>
                    `<option value="${user.id}">
                        ${user.name} (${user.email})
                    </option>`
                )
                .join("");


        // ===============================
        // TASK DROPDOWN
        // ===============================

        $("atask").innerHTML =
            tasks.map(task =>
                `<option value="${task.id}">
                    Task #${task.id} - ${task.project.projectName}
                </option>`
            ).join("");


        // ===============================
        // ANNOTATION DROPDOWN
        // ===============================

        $("rannotation").innerHTML =
            annotations.map(annotation =>
                `<option value="${annotation.id}">
                    Annotation #${annotation.id} - Task #${annotation.task.id}
                </option>`
            ).join("");


        // ===============================
        // PROJECT TABLE
        // ===============================

        $("projectTable").innerHTML =
            projects.map(project =>
                `<tr>
                    <td>${project.id}</td>
                    <td>${project.projectName}</td>
                    <td>${project.dataset.title}</td>
                    <td>${project.status}</td>
                </tr>`
            ).join("");


    } catch (e) {

        alertMsg(e.message, "danger");
    }
}


// ===============================
// CREATE DATASET
// ===============================
async function createDataset() {

    try {

        const file = $("dfile").files[0];

        if (!file) {
            throw new Error("Choose a dataset file");
        }


        const formData = new FormData();

        formData.append("file", file);


        // File upload needs JWT too
        const uploadResponse = await fetch(
            API + "/files/upload",
            {
                method: "POST",

                headers: {
                    "Authorization": `Bearer ${me.token}`
                },

                body: formData
            }
        );


        const uploadData =
            await uploadResponse.json().catch(() => ({}));


        if (!uploadResponse.ok) {

            throw new Error(
                uploadData.error ||
                uploadData.message ||
                "Upload failed"
            );
        }


        // Create dataset record
        await req(
            "/datasets?userId=" + me.id,
            {
                method: "POST",

                body: JSON.stringify({
                    title: $("dtitle").value,
                    description: $("ddesc").value,
                    filePath: uploadData.filePath
                })
            }
        );


        alertMsg("Dataset uploaded successfully");

        await load();


    } catch (e) {

        alertMsg(e.message, "danger");
    }
}


// ===============================
// CREATE PROJECT
// ===============================
async function createProject() {

    try {

        await req(
            `/projects?datasetId=${$("pdataset").value}&ownerId=${me.id}`,
            {
                method: "POST",

                body: JSON.stringify({
                    projectName: $("pname").value
                })
            }
        );


        alertMsg("Project created successfully");

        await load();


    } catch (e) {

        alertMsg(e.message, "danger");
    }
}


// ===============================
// CREATE TASK
// ===============================
async function createTask() {

    try {

        await req(
            `/tasks?projectId=${$("tproject").value}&annotatorId=${$("tannotator").value}`,
            {
                method: "POST",

                body: JSON.stringify({})
            }
        );


        alertMsg("Task assigned successfully");

        await load();


    } catch (e) {

        alertMsg(e.message, "danger");
    }
}


// ===============================
// SUBMIT ANNOTATION
// ===============================
async function submitAnnotation() {

    try {

        await req(
            `/annotations?taskId=${$("atask").value}`,
            {
                method: "POST",

                body: JSON.stringify({
                    label: $("alabel").value
                })
            }
        );


        alertMsg("Annotation submitted successfully");

        await load();


    } catch (e) {

        alertMsg(e.message, "danger");
    }
}


// ===============================
// SUBMIT REVIEW
// ===============================
async function submitReview() {

    try {

        await req(
            `/reviews?annotationId=${$("rannotation").value}&reviewerId=${me.id}`,
            {
                method: "POST",

                body: JSON.stringify({
                    reviewStatus: $("rstatus").value,
                    remarks: $("rremarks").value
                })
            }
        );


        alertMsg("Review saved successfully");

        await load();


    } catch (e) {

        alertMsg(e.message, "danger");
    }
}


// ===============================
// SHOW APPLICATION
// ===============================
function showApp() {

    $("auth").classList.add("d-none");

    $("app").classList.remove("d-none");

    $("logout").classList.remove("d-none");


    $("userInfo").textContent =
        `${me.name} • ${me.role} • ${me.email}`;


    load();
}


// ===============================
// LOGOUT BUTTON
// ===============================
if ($("logout")) {
    $("logout").onclick = logout;
}


// ===============================
// AUTO LOGIN
// ===============================
if (me && me.token) {

    showApp();

}