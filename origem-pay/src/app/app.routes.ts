import { Routes } from '@angular/router';

// Imports apontando para os seus arquivos .ts curtos
import { Login } from './components/login/login';
import { Dashboard } from './components/dashboard/dashboard';
import { PaymentList } from './components/payment-list/payment-list';
import { PaymentCreate } from './components/payment-create/payment-create';

export const routes: Routes = [
  { path: 'login', component: Login },
  { path: 'dashboard', component: Dashboard },
  { path: 'payments', component: PaymentList },
  { path: 'payments/new', component: PaymentCreate },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];
