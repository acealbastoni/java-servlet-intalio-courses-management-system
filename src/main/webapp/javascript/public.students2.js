async function fetchModules() {
    try {
        const response = await fetch('FetchModulesServlet');
        if (!response.ok) {
            throw new Error('Failed to fetch modules');
        }
        const data = await response.json();
        //console.log('Fetched modules:', data); 
        //alert(JSON.stringify(data, null, 2));

        const transformedData = transformData(data);
        //console.log('Transformed Data:', transformedData); // Log the transformed data to the console
        //alert(JSON.stringify(transformedData, null, 2)); // Display the transformed data in an alert

        displayCourses(transformedData); // Display courses and modules on the page
    } catch (error) {
        console.error('Error fetching modules:', error);
    }
}

function transformData(data) {
	const courseMap = {
		1: "Java",
		2: "JavaScript",
		3: "Python",
		4: "C#"
	};

	const moduleMap = {};

	data.forEach(item => {
		const courseName = courseMap[item.courseID];
		if (!moduleMap[courseName]) {
			moduleMap[courseName] = {};
		}

		if (!moduleMap[courseName][item.moduleName]) {
			moduleMap[courseName][item.moduleName] = [];
		}

		moduleMap[courseName][item.moduleName].push(item.pdfFileName);
	});

	const result = Object.keys(moduleMap).map(courseName => {
		return {
			courseName,
			modules: Object.keys(moduleMap[courseName]).map(moduleName => {
				return {
					moduleName,
					pdfFiles: moduleMap[courseName][moduleName]
				};
			})
		};
	});

	return result;
}



// Function to display courses and modules on the page
function displayCourses(courses) {
    const main = document.querySelector('main');
    const defaultFileLink = "https://drive.google.com/u/0/uc?id=1ag1_Z6VL5FKtTirzA3xvm7_HuTkdVaxp&export=download";
    
    courses.forEach(course => {
        const courseSection = document.createElement('section');
        courseSection.classList.add('course');
        courseSection.innerHTML = `
            <h2>${course.courseName}</h2>
            <div class="modules">
                ${course.modules.map(module => `
                    <div class="module">
                        <h3>${module.moduleName}</h3>
                        <ul class="pdf-list">
                            ${module.pdfFiles.length > 0 ? module.pdfFiles.map(pdfFile => `
                                <li class="sub-module"><a href="DownloadServlet?file=${encodeURIComponent(pdfFile)}">${pdfFile}</a></li>
                            `).join('') : `
                                <li class="sub-module"><a href="${defaultFileLink}">Default File</a></li>
                            `}
                        </ul>
                    </div>
                `).join('')}
            </div>
        `;
        main.appendChild(courseSection);
    });
}


// Fetch modules when the page loads
window.onload = function () {
    fetchModules();
};
