SELECT b.budget_id, u.username, c.category_name, b.amount
FROM budgets b
JOIN users u ON b.user_id = u.user_id
JOIN categories c ON b.category_id = c.category_id;
