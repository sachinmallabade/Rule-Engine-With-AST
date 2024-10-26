import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RulePageComponent } from './rule-page.component';

describe('RulePageComponent', () => {
  let component: RulePageComponent;
  let fixture: ComponentFixture<RulePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RulePageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RulePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
