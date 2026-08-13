import {createRouter, createWebHistory} from "vue-router";
import TokenService from "@/services/token.service";

const routes = [
  {
    path: "/home",
    alias: "/",
    name: "home",
    component: () => import("./components/Home.vue"),
    meta: { title: "Главная страница" }
  },
  {
    path: "/users",
    name: "users",
    component: () => import("./components/UsersList.vue"),
    meta: { title: "Список клиентов" , roles: ['ROLE_MANAGER', 'ROLE_ADMIN'] }
  },
  {
    path: "/users/edit/:id",
    name: "edit-user",
    component: () => import("./components/EditUser.vue"),
    meta: { title: "Редактирование пользователя", roles: ['ROLE_MANAGER', 'ROLE_ADMIN'] }
  },
  {
    path: "/users/view/:id",
    name: "ViewUser",
    component: () => import("./components/ViewUser.vue"),
    meta: { title: "Просмотр клиента", roles: ['ROLE_MANAGER', 'ROLE_ADMIN'] }
  },
  {
    path: "/add",
    name: "add-client",
    component: () => import("./components/AddClient.vue"),
    meta: { title: "Добавить клиента", roles: ['ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_REGISTRAR'] }
  },
  {
    path: "/register",
    name: "add-worker",
    component: () => import("./components/AddClient.vue"),
    meta: { title: "Регистрация рабочего", roles: ['ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_REGISTRAR'] }
  },
  {
    path: "/workers",
    name: "workers",
    component: () => import("./components/WorkersList.vue"),
    meta: { title: "Список сотрудников", roles: ['ROLE_MANAGER', 'ROLE_ADMIN'] }
  },
  {
    path: "/workers/view/:id",
    name: "ViewWorker",
    component: () => import("./components/ViewWorker.vue"),
    meta: { title: "Просмотр сотрудника", roles: ['ROLE_MANAGER', 'ROLE_ADMIN'] }
  },
  {
    path: "/directories",
    name: "DirectoryPage",
    component: () => import("./components/DirectoryListPage.vue"),
    meta: { title: "Справочники", roles: ['ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_REGISTRAR'] }
  },
  {
    path: "/directory/cities",
    name: "CitiesDirectoryPage",
    component: () => import("./components/CitiesDirectory.vue"),
    meta: { title: "Справочник городов", roles: ['ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_REGISTRAR'] }
  },
  {
    path: "/directory/regions",
    name: "RegionsDirectoryPage",
    component: () => import("./components/RegionsDirectory.vue"),
    meta: { title: "Справочник регионов", roles: ['ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_REGISTRAR'] }
  },
  {
    path: "/directory/districts",
    name: "DistrictsDirectoryPage",
    component: () => import("./components/DistrictsDirectory.vue"),
    meta: { title: "Справочник районов", roles: ['ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_REGISTRAR'] }
  },
  {
    path: "/directory/city-types",
    name: "CityTypeDirectoryPage",
    component: () => import("./components/CityTypesDirectory.vue"),
    meta: { title: "Справочник типов городов", roles: ['ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_REGISTRAR'] }
  },
  {
    path: "/directory/streets",
    name: "StreetsDirectoryPage",
    component: () => import("./components/StreetsDirectory.vue"),
    meta: { title: "Справочник улиц", roles: ['ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_REGISTRAR'] }
  },
  {
    path: "/log",
    name: "LogPage",
    component: () => import("./components/LogView.vue"),
    meta: { title: "Логи системы", roles: ['ROLE_MANAGER', 'ROLE_ADMIN'] }
  },
  {
    path: "/login",
    name: "login",
    component: () => import("./components/LoginForm.vue"),
    meta: { title: "Вход в систему" }
  },
  {
    path: "/logout",
    name: "logout",
    component: () => import("./components/LogoutForm.vue"),
    meta: { title: "Выход из системы" }
  },
  {
    path: "/403",
    name: "forbidden",
    component: () => import("./components/Forbidden.vue"),
    meta: { title: "Доступ запрещен"}
  },
  {
    path: "/waiting-list",
    name: "WaitingList",
    component: () => import("./components/WaitingList.vue"),
    meta: { title: "Лист ожидания" , roles: ['ROLE_MANAGER', 'ROLE_ADMIN'] }
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, from, next) => {

  const userRoles = TokenService.getRolesFromToken();
  if (to.meta.roles) {
    const hasAccess = to.meta.roles.some(role => userRoles.includes(role));

    if (!hasAccess) {
      return next("/403");
    }
  }
  document.title = to.meta.title || 'Default Title';
  next();
});

export default router;
