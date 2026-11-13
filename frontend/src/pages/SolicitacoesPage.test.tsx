import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { MemoryRouter } from "react-router-dom";
import { beforeEach, describe, expect, it, vi } from "vitest";
import * as cidadaosApi from "../api/cidadaos";
import * as solicitacoesApi from "../api/solicitacoes";
import { SolicitacoesPage } from "./SolicitacoesPage";

vi.mock("../api/cidadaos");
vi.mock("../api/solicitacoes");

function renderPage() {
  return render(
    <MemoryRouter>
      <SolicitacoesPage />
    </MemoryRouter>,
  );
}

describe("SolicitacoesPage", () => {
  beforeEach(() => {
    vi.resetAllMocks();
    vi.mocked(cidadaosApi.listarCidadaos).mockResolvedValue([
      { id: "c1", nome: "Ana", email: "ana@test.com", telefone: "" },
    ]);
    vi.mocked(solicitacoesApi.listarSolicitacoes).mockResolvedValue([]);
  });

  it("desabilita cadastro se não houver cidadãos", async () => {
    vi.mocked(cidadaosApi.listarCidadaos).mockResolvedValue([]);
    renderPage();

    await waitFor(() => {
      expect(screen.getByText(/cadastre um cidadão/i)).toBeInTheDocument();
    });
    expect(
      screen.queryByRole("button", { name: /cadastrar solicitação/i }),
    ).not.toBeInTheDocument();
  });

  it("cria solicitação com cidadão selecionado", async () => {
    const user = userEvent.setup();
    vi.mocked(solicitacoesApi.criarSolicitacao).mockResolvedValue({
      id: "s1",
      cidadaoId: "c1",
      titulo: "Buraco",
      descricao: "Grande",
      bairro: "Centro",
      categoria: "BURACO",
      status: "ABERTA",
    });

    renderPage();
    await screen.findByText(/cidadão responsável/i);

    const [selectCidadao, selectCategoria] = screen.getAllByRole("combobox");
    await user.selectOptions(selectCidadao, "c1");
    await user.selectOptions(selectCategoria, "BURACO");

    const inputs = screen.getAllByRole("textbox");
    await user.type(inputs[0], "Buraco na via");
    await user.type(inputs[1], "Descrição do buraco");
    await user.type(inputs[2], "Centro");

    await user.click(screen.getByRole("button", { name: /cadastrar solicitação/i }));

    await waitFor(() => {
      expect(solicitacoesApi.criarSolicitacao).toHaveBeenCalledWith({
        cidadaoId: "c1",
        titulo: "Buraco na via",
        descricao: "Descrição do buraco",
        bairro: "Centro",
        categoria: "BURACO",
      });
    });
  });
});
