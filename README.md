# restaurant-ordering-app
Desktop app that allows ordering of food digitally.

## Use-Case
Food menus have been around for so long. The process of ordering your food through menus take a huge amount of time - from the waiter handling your menu, choosing your meal for the night, calling a waiter to list your orders, which then the waiter would hand over to the kitchen for the food to be prepared.

The problem with this system is that it takes a huge chunk of time especially from the waiters. With such, it would be better if waiters can have more free time to do other things and improve efficiency.

## Usage
This desktop application can be accessed by two users: Admin and client.

**Admin** - Responsible for creating the menu that would be displayed on the client, accepts and tracks orders, and notifies if a food item is sold out.

**Client** - Is used by the customers to order items from the restaurant. This would generate an invoice based on the ordered items from the restaurant.

## Behind The Scenes
The system utilizes a queue to process orders on a "first come, first served" basis. The amount of orders would be tracked for the day and the total revenue.
