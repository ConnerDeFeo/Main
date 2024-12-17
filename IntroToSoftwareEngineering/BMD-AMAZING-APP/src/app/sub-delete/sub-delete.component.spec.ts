import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubDeleteComponent } from './sub-delete.component';

describe('SubDeleteComponent', () => {
  let component: SubDeleteComponent;
  let fixture: ComponentFixture<SubDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SubDeleteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
