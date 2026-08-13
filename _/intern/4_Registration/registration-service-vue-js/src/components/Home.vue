<template>
  <div class="home">
    <h1>Проект Регистратор</h1>
    <p class="welcome">Добро пожаловать в систему регистрации!</p>
    <div class="content">
      <p>
        Удобный и безопасный сервис для учета данных пользователей.
        Система Регистратор предоставляет удобные инструменты для регистрации и управления данными пользователей, включая личные данные, паспортную информацию и медиа-файлы. В рамках сервиса можно легко и быстро создать запись о человеке, отслеживать изменения в его статусе и истории действий, а также проверять правильность внесенных данных.
      </p>
      <h2>Основные функции:</h2>
      <ul>
        <li><strong>Персонификация:</strong> Поиск, просмотр и редактирование данных пользователей с доступом к истории изменений.</li>
        <li><strong>Справочники:</strong> Выбор населенных пунктов, шаблонов документов и других данных.</li>
        <li><strong>Регистраторы:</strong> Управление регистраторами, ролями и статусами с возможностью контроля доступа.</li>
        <li><strong>История изменений:</strong> Логирование всех действий для повышения безопасности системы.</li>
        <li><strong>Функциональные блоки:</strong> Удобные фильтры для поиска по различным параметрам.</li>
        <li><strong>Регистрация и редактирование:</strong> Создание и редактирование записей, проверка паспортных данных.</li>
        <li><strong>Роли и права доступа:</strong> Различные роли пользователей для гибкого управления доступом.</li>
        <li><strong>Логирование действий:</strong> Все действия логируются для обеспечения прозрачности.</li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
.home {
  text-align: left;
  max-width: 700px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
  background-color: #f7f7f7;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

h1 {
  font-size: 24px;
  color: #333;
  margin-bottom: 10px;
  text-align: center;
}

.welcome {
  font-size: 14px;
  color: #777;
  margin-bottom: 20px;
  text-align: center;
}

.content {
  background-color: white;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  font-size: 18px;
  color: #333;
  margin-top: 15px;
}

ul {
  list-style-type: none;
  padding-left: 10px;
}

li {
  font-size: 14px;
  margin-bottom: 10px;
}

li strong {
  color: #ec0f7f;
}

li p {
  margin-top: 5px;
}
</style>