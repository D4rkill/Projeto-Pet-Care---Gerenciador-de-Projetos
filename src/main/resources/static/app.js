const API = "/api";

let setores = [];
let funcionarios = [];
let projetos = [];

let tutores = [];
let animais = [];
let veterinarios = [];
let consultas = [];

const toast = document.getElementById("toast");

document.addEventListener("DOMContentLoaded", () => {
    configurarFormularios();
    carregarTudo();
});

function configurarFormularios() {
    document.getElementById("formSetor").addEventListener("submit", cadastrarSetor);
    document.getElementById("formFuncionario").addEventListener("submit", cadastrarFuncionario);
    document.getElementById("formProjeto").addEventListener("submit", cadastrarProjeto);
    document.getElementById("formVinculoProjeto").addEventListener("submit", vincularFuncionarioProjeto);

    document.getElementById("formTutor").addEventListener("submit", cadastrarTutor);
    document.getElementById("formAnimal").addEventListener("submit", cadastrarAnimal);
    document.getElementById("formVeterinario").addEventListener("submit", cadastrarVeterinario);
    document.getElementById("formConsulta").addEventListener("submit", agendarConsulta);
}

async function request(url, options = {}) {
    const resposta = await fetch(url, {
        headers: {
            "Content-Type": "application/json"
        },
        ...options
    });

    if (!resposta.ok) {
        const erro = await resposta.json().catch(() => ({
            message: "Erro inesperado na requisição."
        }));

        let mensagem = erro.message || "Erro inesperado.";

        if (erro.fields) {
            const mensagensCampos = Object.values(erro.fields).join(" ");
            mensagem = `${mensagem} ${mensagensCampos}`;
        }

        throw new Error(mensagem);
    }

    if (resposta.status === 204) {
        return null;
    }

    return resposta.json();
}

function avisar(mensagem, erro = false) {
    toast.textContent = mensagem;
    toast.classList.toggle("erro", erro);
    toast.classList.remove("escondido");

    setTimeout(() => {
        toast.classList.add("escondido");
    }, 4200);
}

function escapar(valor) {
    return String(valor ?? "").replace(/[&<>"']/g, caractere => ({
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': "&quot;",
        "'": "&#39;"
    })[caractere]);
}

async function carregarTudo() {
    await Promise.allSettled([
        carregarProjetosModulo(),
        carregarPetCareModulo()
    ]);

    atualizarResumo();
}

async function carregarProjetosModulo() {
    try {
        setores = await request(`${API}/setores`);
        funcionarios = await request(`${API}/funcionarios`);
        projetos = await request(`${API}/projetos`);

        preencherSelectSetores();
        preencherSelectProjetos();
        preencherSelectFuncionarios();
        renderizarProjetos();
    } catch (error) {
        console.error("Erro ao carregar módulo de projetos:", error);
        avisar(`Erro ao carregar projetos: ${error.message}`, true);
    }
}

async function carregarPetCareModulo() {
    tutores = await request(`${API}/petcare/tutores`);
    animais = await request(`${API}/petcare/animais`);
    veterinarios = await request(`${API}/petcare/veterinarios`);
    consultas = await request(`${API}/petcare/consultas`);

    preencherSelectTutores();
    preencherSelectAnimais();
    preencherSelectVeterinarios();
    renderizarConsultas();
}

function atualizarResumo() {
    document.getElementById("totalProjetos").textContent = projetos.length;
    document.getElementById("totalTutores").textContent = tutores.length;
    document.getElementById("totalAnimais").textContent = animais.length;
    document.getElementById("totalVeterinarios").textContent = veterinarios.length;
    document.getElementById("totalConsultas").textContent = consultas.length;
}


async function cadastrarSetor(event) {
    event.preventDefault();

    const payload = {
        nome: document.getElementById("setorNome").value
    };

    try {
        await request(`${API}/setores`, {
            method: "POST",
            body: JSON.stringify(payload)
        });

        event.target.reset();
        avisar("Setor cadastrado com sucesso.");
        await carregarTudo();
    } catch (error) {
        avisar(error.message, true);
    }
}

async function cadastrarFuncionario(event) {
    event.preventDefault();

    const payload = {
        nome: document.getElementById("funcionarioNome").value,
        setorId: Number(document.getElementById("funcionarioSetor").value)
    };

    try {
        await request(`${API}/funcionarios`, {
            method: "POST",
            body: JSON.stringify(payload)
        });

        event.target.reset();
        avisar("Funcionário cadastrado com sucesso.");
        await carregarTudo();
    } catch (error) {
        avisar(error.message, true);
    }
}

async function cadastrarProjeto(event) {
    event.preventDefault();

    const payload = {
        descricao: document.getElementById("projetoDescricao").value,
        dataInicio: document.getElementById("projetoDataInicio").value,
        dataFim: document.getElementById("projetoDataFim").value
    };

    try {
        await request(`${API}/projetos`, {
            method: "POST",
            body: JSON.stringify(payload)
        });

        event.target.reset();
        avisar("Projeto cadastrado com sucesso.");
        await carregarTudo();
    } catch (error) {
        avisar(error.message, true);
    }
}

async function vincularFuncionarioProjeto(event) {
    event.preventDefault();

    const projetoId = Number(document.getElementById("vinculoProjeto").value);
    const funcionarioId = Number(document.getElementById("vinculoFuncionario").value);

    if (!projetoId || !funcionarioId) {
        avisar("Cadastre pelo menos um projeto e um funcionário antes de vincular.", true);
        return;
    }

    try {
        await request(`${API}/projetos/${projetoId}/funcionarios/${funcionarioId}`, {
            method: "POST"
        });

        avisar("Funcionário vinculado ao projeto com sucesso.");
        await carregarTudo();
    } catch (error) {
        avisar(error.message, true);
    }
}

function preencherSelectSetores() {
    const select = document.getElementById("funcionarioSetor");

    if (!setores.length) {
        select.innerHTML = `<option value="">Cadastre um setor primeiro</option>`;
        return;
    }

    select.innerHTML = setores.map(setor => `
        <option value="${setor.id}">
            ${escapar(setor.nome)}
        </option>
    `).join("");
}

function preencherSelectProjetos() {
    const select = document.getElementById("vinculoProjeto");

    if (!projetos.length) {
        select.innerHTML = `<option value="">Cadastre um projeto primeiro</option>`;
        return;
    }

    select.innerHTML = projetos.map(projeto => `
        <option value="${projeto.id}">
            ${escapar(projeto.descricao)}
        </option>
    `).join("");
}

function preencherSelectFuncionarios() {
    const select = document.getElementById("vinculoFuncionario");

    if (!funcionarios.length) {
        select.innerHTML = `<option value="">Cadastre um funcionário primeiro</option>`;
        return;
    }

    select.innerHTML = funcionarios.map(funcionario => `
        <option value="${funcionario.id}">
            ${escapar(funcionario.nome)} - ${escapar(funcionario.setorNome)}
        </option>
    `).join("");
}

function renderizarProjetos() {
    const area = document.getElementById("listaProjetos");

    if (!projetos.length) {
        area.innerHTML = `<p class="vazio">Nenhum projeto cadastrado.</p>`;
        return;
    }

    area.innerHTML = `
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Descrição</th>
                    <th>Período</th>
                    <th>Funcionários</th>
                </tr>
            </thead>
            <tbody>
                ${projetos.map(projeto => `
                    <tr>
                        <td>${projeto.id}</td>
                        <td>${escapar(projeto.descricao)}</td>
                        <td>${escapar(projeto.dataInicio)} até ${escapar(projeto.dataFim)}</td>
                        <td>
                            ${
                                projeto.funcionarios && projeto.funcionarios.length
                                    ? projeto.funcionarios.map(f => escapar(f.nome)).join(", ")
                                    : "Sem funcionários vinculados"
                            }
                        </td>
                    </tr>
                `).join("")}
            </tbody>
        </table>
    `;
}


async function cadastrarTutor(event) {
    event.preventDefault();

    const payload = {
        nome: document.getElementById("tutorNome").value,
        email: document.getElementById("tutorEmail").value,
        telefone: document.getElementById("tutorTelefone").value
    };

    try {
        await request(`${API}/petcare/tutores`, {
            method: "POST",
            body: JSON.stringify(payload)
        });

        event.target.reset();
        avisar("Tutor cadastrado com sucesso.");
        await carregarTudo();
    } catch (error) {
        avisar(error.message, true);
    }
}

async function cadastrarAnimal(event) {
    event.preventDefault();

    const payload = {
        nome: document.getElementById("animalNome").value,
        especie: document.getElementById("animalEspecie").value,
        raca: document.getElementById("animalRaca").value,
        idade: Number(document.getElementById("animalIdade").value),
        porte: document.getElementById("animalPorte").value,
        tutorId: Number(document.getElementById("animalTutor").value)
    };

    try {
        await request(`${API}/petcare/animais`, {
            method: "POST",
            body: JSON.stringify(payload)
        });

        event.target.reset();
        avisar("Animal cadastrado com sucesso.");
        await carregarTudo();
    } catch (error) {
        avisar(error.message, true);
    }
}

async function cadastrarVeterinario(event) {
    event.preventDefault();

    const payload = {
        nome: document.getElementById("veterinarioNome").value,
        crmv: document.getElementById("veterinarioCrmv").value,
        especialidade: document.getElementById("veterinarioEspecialidade").value
    };

    try {
        await request(`${API}/petcare/veterinarios`, {
            method: "POST",
            body: JSON.stringify(payload)
        });

        event.target.reset();
        avisar("Veterinário cadastrado com sucesso.");
        await carregarTudo();
    } catch (error) {
        avisar(error.message, true);
    }
}

async function agendarConsulta(event) {
    event.preventDefault();

    const payload = {
        dataHora: document.getElementById("consultaDataHora").value,
        especialidadeConsulta: document.getElementById("consultaEspecialidade").value,
        observacao: document.getElementById("consultaObservacao").value,
        animalId: Number(document.getElementById("consultaAnimal").value),
        veterinarioId: Number(document.getElementById("consultaVeterinario").value)
    };

    try {
        await request(`${API}/petcare/consultas`, {
            method: "POST",
            body: JSON.stringify(payload)
        });

        event.target.reset();
        avisar("Consulta agendada com sucesso.");
        await carregarTudo();
    } catch (error) {
        avisar(error.message, true);
    }
}

function preencherSelectTutores() {
    const select = document.getElementById("animalTutor");

    select.innerHTML = tutores.map(tutor => `
        <option value="${tutor.id}">
            ${escapar(tutor.nome)}
        </option>
    `).join("");
}

function preencherSelectAnimais() {
    const select = document.getElementById("consultaAnimal");

    select.innerHTML = animais.map(animal => `
        <option value="${animal.id}">
            ${escapar(animal.nome)} - Tutor: ${escapar(animal.tutorNome)}
        </option>
    `).join("");
}

function preencherSelectVeterinarios() {
    const select = document.getElementById("consultaVeterinario");

    select.innerHTML = veterinarios.map(veterinario => `
        <option value="${veterinario.id}">
            ${escapar(veterinario.nome)} - ${formatarEspecialidade(veterinario.especialidade)}
        </option>
    `).join("");
}

function renderizarConsultas() {
    const area = document.getElementById("listaConsultas");

    if (!consultas.length) {
        area.innerHTML = `<p class="vazio">Nenhuma consulta cadastrada.</p>`;
        return;
    }

    area.innerHTML = `
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Data/Hora</th>
                    <th>Animal</th>
                    <th>Tutor</th>
                    <th>Veterinário</th>
                    <th>Especialidade</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                ${consultas.map(consulta => `
                    <tr>
                        <td>${consulta.id}</td>
                        <td>${formatarDataHora(consulta.dataHora)}</td>
                        <td>${escapar(consulta.animalNome)}</td>
                        <td>${escapar(consulta.tutorNome)}</td>
                        <td>${escapar(consulta.veterinarioNome)}</td>
                        <td>${formatarEspecialidade(consulta.especialidadeConsulta)}</td>
                        <td>
                            <span class="badge ${String(consulta.status).toLowerCase()}">
                                ${formatarStatus(consulta.status)}
                            </span>
                        </td>
                    </tr>
                `).join("")}
            </tbody>
        </table>
    `;
}

function formatarEspecialidade(valor) {
    const mapa = {
        CLINICA_GERAL: "Clínica Geral",
        DERMATOLOGIA: "Dermatologia",
        CARDIOLOGIA: "Cardiologia",
        ORTOPEDIA: "Ortopedia",
        ODONTOLOGIA: "Odontologia",
        VACINACAO: "Vacinação"
    };

    return mapa[valor] || valor;
}

function formatarStatus(valor) {
    const mapa = {
        AGENDADA: "Agendada",
        CONCLUIDA: "Concluída",
        CANCELADA: "Cancelada"
    };

    return mapa[valor] || valor;
}

function formatarDataHora(valor) {
    if (!valor) {
        return "";
    }

    return valor.replace("T", " ").slice(0, 16);
}


async function testarConflitoAgenda() {
    if (!consultas.length) {
        avisar("Cadastre uma consulta primeiro para testar conflito de agenda.", true);
        return;
    }

    const consultaBase = consultas[0];

    const payload = {
        dataHora: consultaBase.dataHora,
        especialidadeConsulta: consultaBase.especialidadeConsulta,
        observacao: "Teste automático de conflito de agenda.",
        animalId: consultaBase.animalId,
        veterinarioId: consultaBase.veterinarioId
    };

    try {
        await request(`${API}/petcare/consultas`, {
            method: "POST",
            body: JSON.stringify(payload)
        });

        avisar("A consulta foi criada, então o conflito não foi detectado.", true);
        await carregarTudo();
    } catch (error) {
        avisar(`Regra funcionando: ${error.message}`, true);
    }
}

async function testarEspecialidadeErrada() {
    if (!animais.length || !veterinarios.length) {
        avisar("Cadastre pelo menos um animal e um veterinário primeiro.", true);
        return;
    }

    const veterinario = veterinarios[0];

    const especialidades = [
        "CLINICA_GERAL",
        "DERMATOLOGIA",
        "CARDIOLOGIA",
        "ORTOPEDIA",
        "ODONTOLOGIA",
        "VACINACAO"
    ];

    const especialidadeErrada = especialidades.find(e => e !== veterinario.especialidade);

    const dataFutura = new Date();
    dataFutura.setDate(dataFutura.getDate() + 7);
    dataFutura.setHours(15, 30, 0, 0);

    const payload = {
        dataHora: dataFutura.toISOString().slice(0, 19),
        especialidadeConsulta: especialidadeErrada,
        observacao: "Teste automático de especialidade errada.",
        animalId: animais[0].id,
        veterinarioId: veterinario.id
    };

    try {
        await request(`${API}/petcare/consultas`, {
            method: "POST",
            body: JSON.stringify(payload)
        });

        avisar("A consulta foi criada, então a especialidade errada não foi bloqueada.", true);
        await carregarTudo();
    } catch (error) {
        avisar(`Regra funcionando: ${error.message}`, true);
    }
}