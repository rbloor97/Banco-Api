import { Routes } from '@angular/router';
import { LayoutComponent } from './shared/components/layout/layout.component';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: '',
        redirectTo: 'clientes',
        pathMatch: 'full'
      },
      {
        path: 'clientes',
        loadComponent: () => import('./features/clientes/clientes.component').then(c => c.ClientesComponent)
      },
      {
        path: 'cuentas',
        loadComponent: () => import('./features/cuentas/cuentas.component').then(c => c.CuentasComponent)
      },
      {
        path: 'movimientos',
        loadComponent: () => import('./features/movimientos/movimientos.component').then(c => c.MovimientosComponent)
      },
      {
        path: 'reportes',
        loadComponent: () => import('./features/reportes/reportes.component').then(c => c.ReportesComponent)
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'clientes'
  }
];
