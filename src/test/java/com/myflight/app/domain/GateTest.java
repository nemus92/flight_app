package com.myflight.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myflight.app.web.rest.TestUtil;

public class GateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gate.class);
        Gate gate1 = new Gate();
        gate1.setId(1L);
        Gate gate2 = new Gate();
        gate2.setId(gate1.getId());
        assertThat(gate1).isEqualTo(gate2);
        gate2.setId(2L);
        assertThat(gate1).isNotEqualTo(gate2);
        gate1.setId(null);
        assertThat(gate1).isNotEqualTo(gate2);
    }
}
