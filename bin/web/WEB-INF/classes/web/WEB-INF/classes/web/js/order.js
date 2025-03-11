/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function addToOrder(itemName, itemPrice) {
    let order = JSON.parse(localStorage.getItem('order')) || [];
    const existingItem = order.find(item => item.name === itemName);
    if (existingItem) {
        existingItem.quantity += 1;
    } else {
        order.push({name: itemName, price: itemPrice, quantity: 1, served: false});
    }
    localStorage.setItem('order', JSON.stringify(order));
    console.log(order);
}

function loadOrder() {
    let order = JSON.parse(localStorage.getItem('order')) || [];
    const orderList = document.getElementById('order-list');
    let totalServed = 0;
    orderList.innerHTML = '';
    order.forEach(item => {
        const itemTotal = item.price * item.quantity;
        const status = item.served ? 'Served' : 'Not Served';
        if (item.served) {
            totalServed += itemTotal;
        }
        const row = `
            <tr>
                <td>${item.name}</td>
                <td>${item.quantity}</td>
                <td>$${item.price.toFixed(2)}</td>
                <td>$${itemTotal.toFixed(2)}</td>
                <td>${status}</td>
            </tr>
        `;
        orderList.innerHTML += row;
    });
    document.getElementById('total-served').innerText = `$${totalServed.toFixed(2)}`;
}

function payOrder() {
    alert('Payment successful!');
    localStorage.removeItem('order');
    window.location.reload(); // Reload the page to clear the order display
}

window.onload = function () {
    if (document.getElementById('order-list')) {
        loadOrder();
    }
};
