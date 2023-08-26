// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';



var ctx = document.getElementById("pieChart");
var myPieChart = new Chart(ctx, {
  type: 'pie',
  data: {
    labels: ["Pending", "In Progress", "To Ship", "To Receive"],
    datasets: [{
      data: [12.21, 15.58, 11.25, 8.32],
      backgroundColor: ['#ffc107', '#007bff', '#28a745', '#dc3545'],
    }],
  },
});

/*var ctx = document.getElementById("pieChart");
var myPieChart = new Chart(ctx, {
  type: 'pie',
  data: {
    labels: labels,
    datasets: [{
      data: values,
      backgroundColor: ['#ffc107', '#007bff', '#28a745', '#dc3545'],
    }],
  },
});*/
