import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { MovimientosComponent } from './movimientos.component';

describe('MovimientosComponent', () => {
  let component: MovimientosComponent;
  let fixture: ComponentFixture<MovimientosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        MovimientosComponent,
        HttpClientTestingModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MovimientosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
