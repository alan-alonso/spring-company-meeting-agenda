import Vue from 'vue';
import Vuetify from 'vuetify';

Vue.use(Vuetify);

import { shallowMount, createLocalVue } from '@vue/test-utils';
import HelloWorld from '@/components/HelloWorld.vue';

const localVue = createLocalVue();
const vuetify = new Vuetify();

describe('HelloWorld.vue', () => {
  it('render the component', () => {
    const wrapper = shallowMount(HelloWorld, {
      localVue,
      vuetify
    });
    expect(wrapper.isVueInstance()).toBeTruthy();
  });
});
